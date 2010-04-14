/*
 * Created on 8-April-2010
 *
 */

package be.ibridge.kettle.QRSend;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pentaho.di.core.Const;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.ui.core.widget.TextVar;
import org.pentaho.di.ui.trans.step.BaseStepDialog;

public class QRSendPluginDialog extends BaseStepDialog implements StepDialogInterface
{
	private QRSendPluginMeta input;
	//private ValueMetaAndData value;

	private Label label0;
	private TextVar Text0;
	private Label label1;
	private Text Text1;
	private Label label2;
	private Text Text2;
	private Label label3;
	private Combo combo0;
	private Label label4;
	private Text Text4;
	
	public QRSendPluginDialog(Shell parent, Object in, TransMeta transMeta, String sname)
	{
		super(parent, (BaseStepMeta)in, transMeta, sname);
		input=(QRSendPluginMeta)in;
	}

	public String open()
	{
		Shell parent = getParent();
		Display display = parent.getDisplay();
		
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MIN | SWT.MAX);
		shell.setSize(408, 270);
        setShellImage(shell, input);

		ModifyListener lsMod = new ModifyListener() 
		{
			public void modifyText(ModifyEvent e) 
			{
				input.setChanged();
			}
		};
		changed = input.hasChanged();

		FormLayout formLayout = new FormLayout ();
		formLayout.marginWidth  = Const.FORM_MARGIN;
		formLayout.marginHeight = Const.FORM_MARGIN;

		//shell.setLayout(formLayout);
		shell.setText(Messages.getString("QRCodePluginDialog.Shell.Title")); //$NON-NLS-1$
		
///////////////////////////////////////////////////////////////////////////////////
		//StepName
		wlStepname=new Label(shell, SWT.RIGHT);
		wlStepname.setText(Messages.getString("QRCodePluginDialog.StepName.Label")); //$NON-NLS-1$
		wlStepname.setBounds(100,10,80,20);
		wStepname=new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wStepname.setText(stepname);
		wStepname.addModifyListener(lsMod);
		wStepname.setBounds(190,10,200,20);	
		//QRColumn 
		label0 = new Label (shell, SWT.NONE);
		label0.setText("QRColumn :");
		label0.setBounds(100,40,60,20);
		Text0 = new TextVar (transMeta, shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		Text0.addModifyListener(lsMod);
		Text0.setBounds(190,40,200,20);
		//Size height
		label1 = new Label (shell, SWT.NONE);
		label1.setText("Size x :");
		label1.setBounds(140,70,30,20);
		Text1 = new Text (shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		Text1.addModifyListener(lsMod);
		Text1.setBounds(190,70,200,20);
		// Size large
		label2 = new Label (shell, SWT.NONE);
		label2.setText("Size y :");
		label2.setBounds(140,100,30,20);
		Text2 = new Text (shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		Text2.addModifyListener(lsMod);
		Text2.setBounds(190,100,200,20);
		// Pic format
		label3 = new Label (shell, SWT.NONE);
		label3.setText("Picture format :");
		label3.setBounds(100,130,70,20);
		combo0 = new Combo(shell, SWT.NULL);
		combo0.setBounds(190,130,50,20);
	    String[] DST = new String[]{"png", "gif"};
	    for(int i=0; i<DST.length; i++)
	    combo0.add(DST[i]);
	    combo0.addModifyListener(lsMod);
		//Destination Dir 
		label4 = new Label (shell, SWT.NONE);
		label4.setText("Destination Dir :");
		label4.setBounds(100,160,80,20);
		Text4 = new Text (shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		Text4.addModifyListener(lsMod);
		Text4.setBounds(190,160,200,20); 
		//OK Button
		Button OKBut = new Button (shell, SWT.PUSH);
		OKBut.setText ("&Ok");
		OKBut.setBounds(10, 200, 100, 30);
		OKBut.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ok();
			}
		});
		//CANCEL Button
		Button CancelBut = new Button (shell, SWT.PUSH);
		CancelBut.setText ("&Cancel");
		CancelBut.setBounds(120, 200, 100, 30);
		CancelBut.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				cancel();
			}
		});
				lsDef=new SelectionAdapter() { public void widgetDefaultSelected(SelectionEvent e) { ok(); } };
		wStepname.addSelectionListener( lsDef );
		
		shell.addShellListener(	new ShellAdapter() { public void shellClosed(ShellEvent e) { cancel(); } } );

		// Set the shell size, based upon previous time...
		//setSize();
		
		getData();
		input.setChanged(changed);
	
		shell.open();
		while (!shell.isDisposed())
		{
		    if (!display.readAndDispatch()) display.sleep();
		}
		return stepname;
	}

	public void getData()
	{
			Text0.setText(Const.NVL(input.getQRColumn(), ""));
			Text1.setText(Const.NVL(input.getQRSizeX(), ""));
			Text2.setText(Const.NVL(input.getQRSizeY(), ""));
			//Text3.setText(Const.NVL(input.getQRPicFormat(), ""));
			combo0.setText(Const.NVL(input.getQRPicFormat(), ""));
			Text4.setText(Const.NVL(input.getQRDestDir(), ""));
	}
	
	private void cancel()
	{
		stepname=null;
		input.setChanged(changed);
		dispose();
	}
	
	private void ok()
	{
		stepname = wStepname.getText(); // return value
	    input.setQRColumn(Text0.getText());
		input.setQRSizeX(Text1.getText());
	    input.setQRSizeY(Text2.getText());
		//input.setQRPicFormat(Text3.getText());
	    input.setQRPicFormat(combo0.getText());
	    input.setQRDestDir(Text4.getText());
		dispose();
	}
	
}
