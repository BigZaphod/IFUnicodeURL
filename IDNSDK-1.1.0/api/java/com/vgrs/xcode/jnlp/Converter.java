package com.vgrs.xcode.jnlp;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import com.vgrs.xcode.ext.*;
import com.vgrs.xcode.idna.*;
import com.vgrs.xcode.util.*;
import com.vgrs.xcode.common.*;



/**
 * A Graphical User Interface for running IDNA conversions.
 */
public class Converter extends JFrame
implements ActionListener, CaretListener {

	private static final String TITLE = "IDN Conversion Tool";
	private static final String DELIMITER = "\n";
	private static final Font DEFAULT_FONT = new Font("Monospaced",Font.PLAIN,12);

	private static final String ICON_URL = "images/VeriSign-Small.gif";

	private static final int ANIM_WIDTH = 55;
	private static final int ANIM_HEIGHT = 23;
	private static final int ANIM_FPS = 10;
	private static final String[] ANIM_URLS = {
		"images/Cheetah/Cheetah-1.gif",
		"images/Cheetah/Cheetah-2.gif",
		"images/Cheetah/Cheetah-3.gif",
		"images/Cheetah/Cheetah-4.gif",
		"images/Cheetah/Cheetah-5.gif",
		"images/Cheetah/Cheetah-6.gif",
		"images/Cheetah/Cheetah-7.gif",
		"images/Cheetah/Cheetah-8.gif",
	};

	private static ClassLoader loader = Converter.class.getClassLoader();
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();

	// Menubar
	private Container content;
	private JCheckBoxMenuItem useStd3AsciiRules;
	private JCheckBoxMenuItem disallowUnassigned;
	private JCheckBoxMenuItem checkRoundTrip;
	private JCheckBoxMenuItem maskAceErrors;
	private ButtonGroup radioInput;
	private ButtonGroup radioOutput;
	private Animation anim;

	// GUI
	private	JPanel io;
	private	JTextArea input;
	private	JTextArea output;
	private	JLabel status;

	// IDNA
	private Convert convert = null;
	private Race race;
	private Punycode punycode;
	private Nameprep nameprep;
	private Idna iRace;
	private Idna iPunycode;

	private int totalRecords;
	private int errorRecords;
	private String inputType;
	private String outputType;
	private	int lastLineCount = 1;
	private	String lastInputText = null;



	/**
	 * Start the GUI
	 */
	public static void main(String args[]) {
		Converter converter = new Converter();
	}


	/**
	 * Create a Converter object which will display on the screen
	 */
	public Converter() {
		super(TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setJMenuBar(this.initMenuBar());

		this.initIdnaObjects();

		content = this.getContentPane();
		content.setLayout(new BorderLayout());

		io = new JPanel();
		io.setLayout(new BoxLayout(io, BoxLayout.X_AXIS));

		input = new JTextArea();
		input.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		input.addCaretListener(this);

		output = new JTextArea();
		output.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		output.setEditable(false);

		io.add(input);
		io.add(output);

		content.add(new JScrollPane(io));

		status = new JLabel(loadIcon(ICON_URL), SwingConstants.LEFT);
		content.add(status, BorderLayout.SOUTH);

		initFontSupport();

		this.pack();
		this.setLocation(new Point(500,100));
		this.setSize(800,600);
		this.show();
	}

	private JMenuBar initMenuBar() {
		JMenuBar menubar = new JMenuBar();

		JMenu options = new JMenu("Options");
		options.setMnemonic('O');
		useStd3AsciiRules = new JCheckBoxMenuItem("Apply DNS Rules",true);
		useStd3AsciiRules.addActionListener(this);
		options.add(useStd3AsciiRules);
		disallowUnassigned = new JCheckBoxMenuItem("Disallow Unassigned Unicode",true);
		disallowUnassigned.addActionListener(this);
		options.add(disallowUnassigned);
		checkRoundTrip = new JCheckBoxMenuItem("Apply Round-Trip Checking",true);
		checkRoundTrip.addActionListener(this);
		options.add(checkRoundTrip);
		maskAceErrors = new JCheckBoxMenuItem("Mask Ace Errors",false);
		maskAceErrors.addActionListener(this);
		options.add(maskAceErrors);
		options.addSeparator();
		JMenuItem explicit = new JMenuItem("Convert");
		explicit.addActionListener(this);
		options.add(explicit);
		menubar.add(options);

		JMenu inputtype = new JMenu("Input Type");
		radioInput = new ButtonGroup();
		initInputEncodingMenu(inputtype,radioInput);
		menubar.add(inputtype);

		JMenu outputtype = new JMenu("Output Type");
		radioOutput = new ButtonGroup();
		initOutputEncodingMenu(outputtype,radioOutput);
		menubar.add(outputtype);

		anim = new Animation(ANIM_URLS,ANIM_WIDTH,ANIM_HEIGHT,ANIM_FPS);

		menubar.add(Box.createHorizontalGlue());
		menubar.add(anim);

		return menubar;
	}

	private void initIdnaObjects() {
		initAce();
		initNameprep();
		initIdna();
		initConvert();
	}
	private void initAce() {
		race = new Race(useStd3AsciiRules.getState());
		punycode = new Punycode(useStd3AsciiRules.getState());
	}
	private void initNameprep() {
		try {
			nameprep = new Nameprep(!disallowUnassigned.getState());
		} catch (XcodeException x) {
			JOptionPane.showMessageDialog(this,x.getMessage(),
			"Unable to initialize Nameprep routine",JOptionPane.WARNING_MESSAGE);
		}
	}
	private void initIdna() {
		try {
			iRace = new Idna(race,nameprep,
			!maskAceErrors.getState(),checkRoundTrip.getState());
			iPunycode = new Idna(punycode,nameprep,
			!maskAceErrors.getState(),checkRoundTrip.getState());
		} catch (XcodeException x) {
			JOptionPane.showMessageDialog(this,x.getMessage(),
			"Unable to initialize Idna routines",JOptionPane.WARNING_MESSAGE);
		}
	}
	private void initConvert() {
		try {
			convert = new Convert(iRace,iPunycode);
		} catch (XcodeException x) {
			JOptionPane.showMessageDialog(this,x.getMessage(),
			"Unable to initialize Conversion routine",JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Implemented via contract with CaretListener.  Conditionally executes the
	 * conversion if input panel content has changed.
	 * @param e A CaretEvent
	 */
	public void caretUpdate(CaretEvent e) {
		int thisLineCount = input.getLineCount();
		String thisInputText = input.getText().trim();
		if (thisLineCount != lastLineCount && !thisInputText.equals(lastInputText)) {
			convert();
			lastInputText = thisInputText;
		}
		lastLineCount = thisLineCount;
	}

	/**
	 * Implemented via contract with ActionListener.  Updates internal data
	 * structures when option flags change.  Runs conversion when appropriate.
	 * @param e An ActionEvent
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source instanceof JCheckBoxMenuItem) {
			JCheckBoxMenuItem item = (JCheckBoxMenuItem)source;
			if (item.equals(useStd3AsciiRules)) {initAce();}
			else if (item.equals(disallowUnassigned)) {initNameprep();}
			else if (item.equals(checkRoundTrip)) { }
			else if (item.equals(maskAceErrors)) { }
			initIdna();
			initConvert();
			convert();
		}
		else if (source instanceof JRadioButtonMenuItem) {
			convert();
		}
		else if (source instanceof JMenuItem) {
			JMenuItem item = (JMenuItem)source;
			if (item.getText().equals("Convert")) {convert();}
		}
	}

	private void convert() {
		output.setText("");
		status.setText("One moment please...");
		this.update(this.getGraphics());
		totalRecords = 0;
		errorRecords = 0;
		StringTokenizer tokens = new StringTokenizer(input.getText(),DELIMITER,true);
		String token;
		output.setText("");
		while (tokens.hasMoreTokens()) {
			token = tokens.nextToken();
			if (token.equals(DELIMITER)) {output.append(DELIMITER);}
			else {output.append(convert(token));}
			anim.next();
		}
		status.setText("    "+totalRecords+" records    "+errorRecords+" errors");
	}

	private String convert(String input) {
		String output;

		if (input == null) {return "";}
		input = input.trim();
		if (input.length() == 0 || input.charAt(0) == '#') {return input;}

		totalRecords++;
		String inputType = getSelection(radioInput);
		String outputType = getSelection(radioOutput);
		String originalOutputType = outputType;

		try {
			if (inputType.equals("RACE")) {}
			else if (inputType.equals("Punycode")) {}
			else if (inputType.equals("Native")) {
				inputType = "UnicodeBigUnmarked";
			} else if (inputType.equals("UTF-32")) {
				input = new String(Unicode.decode(Hex.decodeInts(input)));
				inputType = "UnicodeBigUnmarked";
			} else if (inputType.equals("UTF-16")) {
				input = new String(Hex.decodeChars(input));
				inputType = "UnicodeBigUnmarked";
			} else {
				// new String(byte[]) is a dangerous method, so we avoid it here.
				byte[] b = Hex.decodeBytes(input);
				char[] c = new char[b.length];
				for (int i=0; i<c.length; i++) {c[i]=(char)(b[i]&0xff);}
				input = new String(c);
			}

			if (outputType.equals("RACE")) {}
			else if (outputType.equals("Punycode")) {}
			else if (outputType.equals("Native")) {
				outputType = "UnicodeBigUnmarked";
			} else if (outputType.equals("UTF-32")) {
				outputType = "UnicodeBigUnmarked";
			} else if (outputType.equals("UTF-16")) {
				outputType = "UnicodeBigUnmarked";
			}

			if (!inputType.equals(outputType)) {
				if (inputType.equals("UnicodeBigUnmarked")) {input = unpackUtf16(input);}
				output = convert.execute(input,inputType,outputType);
				if (outputType.equals("UnicodeBigUnmarked")) {output = packUtf16(output);}
			} else {
				output = input;
			}

			if (originalOutputType.equals("RACE")) {}
			else if (originalOutputType.equals("Punycode")) {}
			else if (originalOutputType.equals("Native")) {}
			else if (originalOutputType.equals("UTF-32")) {
				output = Hex.encode(Unicode.encode(output.toCharArray()));
			} else {
				output = Hex.encode(output.toCharArray());
			}
		}
		catch (XcodeException x) {
			errorRecords++;
			return "Error:"+x.getCode()+" "+x.getMessage();
		}

		return output;
	}

	private String getSelection(ButtonGroup buttongroup) {
		Enumeration e = buttongroup.getElements();
		while (e.hasMoreElements()) {
			AbstractButton ab = (AbstractButton)e.nextElement();
			if (ab.isSelected()) {return ab.getText();}
		}
		return null;
	}

	/**
	 * Reassemble UTF-16 data which has been mangled during encoding.
	 * If a String object is used to hold data encoded using the
	 * "UnicodeBigUnmarked" encoding type, then each 16-bit value will be
	 * spread across two 16-bit char primitives.  This method reassembles those
	 * two 16-bit values into a single UTF-16 character.
	 * @param encoding The String holding the encoded data
	 */
	public static String packUtf16(String encoding) throws XcodeException {
		byte[] b = Native.getEncoding(encoding);
		if (b.length % 2 != 0) {throw XcodeError.NATIVE_INVALID_ENCODING();}
		char[] c = new char[b.length/2];
		for (int i=0; i<c.length; i++) {
			c[i] = (char)(((b[i*2]&0xff)<<8) | (b[(i*2)+1]&0xff));
		}
		return new String(c);
	}

	/**
	 * Disassemble UTF-16 data in preparation for decoding.
	 * Java native encoding methods use only 8 bits of data at a time.  When
	 * decoding UTF-16 data using the "UnicodeBigUnmarked" encoding type, it
	 * is necessary to break each 16-bit piece of data into two 16-bit character
	 * primitives.
	 * @param utf16 The String holding the data to be encoded
	 */
	public static String unpackUtf16(String utf16) throws XcodeException {
		char[] a = utf16.toCharArray();
		char[] b = new char[a.length*2];
		for (int i=0; i<a.length; i++) {
			b[i*2] = (char)(a[i]>>8);
			b[(i*2)+1] = (char)(a[i]&0xff);
		}
		return new String(b);
	}

	private void applyRadioButton(JMenu jmenu, ButtonGroup bg,
	String title, boolean state) {
		JRadioButtonMenuItem tmp = new JRadioButtonMenuItem(title,state);
		jmenu.add(tmp);
		bg.add(tmp);
		tmp.addActionListener(this);
	}

	private void initInputEncodingMenu(JMenu jmenu, ButtonGroup bg) {
		applyRadioButton(jmenu,bg,"RACE",true);
		applyRadioButton(jmenu,bg,"Punycode",false);
		applyRadioButton(jmenu,bg,"Native",false);
		JMenu hex = new JMenu("Hex");
		applyRadioButton(hex,bg,"UTF-32",false);
		applyRadioButton(hex,bg,"UTF-16",false);
		applyRadioButton(hex,bg,"UTF-8",false);
		applyRadioButton(hex,bg,"Big5",false);
		applyRadioButton(hex,bg,"EUC_CN",false);
		applyRadioButton(hex,bg,"EUC_JP",false);
		applyRadioButton(hex,bg,"EUC_KR",false);
		applyRadioButton(hex,bg,"EUC_TW",false);
		applyRadioButton(hex,bg,"GBK",false);
		applyRadioButton(hex,bg,"ISO2022JP",false);
		applyRadioButton(hex,bg,"ISO2022KR",false);
		applyRadioButton(hex,bg,"KOI8_R",false);
		applyRadioButton(hex,bg,"SJIS",false);
		jmenu.add(hex);
	}

	private void initOutputEncodingMenu(JMenu jmenu, ButtonGroup bg) {
		applyRadioButton(jmenu,bg,"RACE",false);
		applyRadioButton(jmenu,bg,"Punycode",true);
		applyRadioButton(jmenu,bg,"Native",false);
		JMenu hex = new JMenu("Hex");
		applyRadioButton(hex,bg,"UTF-32",false);
		applyRadioButton(hex,bg,"UTF-16",false);
		applyRadioButton(hex,bg,"UTF-8",false);
		applyRadioButton(hex,bg,"Big5",false);
		applyRadioButton(hex,bg,"EUC_CN",false);
		applyRadioButton(hex,bg,"EUC_JP",false);
		applyRadioButton(hex,bg,"EUC_KR",false);
		applyRadioButton(hex,bg,"EUC_TW",false);
		applyRadioButton(hex,bg,"GBK",false);
		applyRadioButton(hex,bg,"ISO2022JP",false);
		applyRadioButton(hex,bg,"ISO2022KR",false);
		applyRadioButton(hex,bg,"KOI8_R",false);
		applyRadioButton(hex,bg,"SJIS",false);
		jmenu.add(hex);
	}


	private ImageIcon loadIcon(String resource) {
		return new ImageIcon(loader.getResource(resource));
	}


	private void initFontSupport() {
		input.setFont(DEFAULT_FONT);
		output.setFont(DEFAULT_FONT);
	}

}
