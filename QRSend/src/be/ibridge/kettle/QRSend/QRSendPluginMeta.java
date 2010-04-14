package be.ibridge.kettle.QRSend;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.core.CheckResult;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.Counter;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.w3c.dom.Node;

/*
 * Created on 02-jun-2003
 *
 */

public class QRSendPluginMeta extends BaseStepMeta implements StepMetaInterface
{
	//private ValueMetaAndData value;
	
	private String QRColumn;
	private String QRSizeX;
	private String QRSizeY;
	private String QRPicFormat;
	private String QRDestDir;
	
	
	public QRSendPluginMeta()
	{
		super(); // allocate BaseStepInfo
	}
		
	// Column
	public String getQRColumn() {
		return QRColumn;
	}
	public void setQRColumn(String QRColumn) {
		this.QRColumn = QRColumn;
	}
	// SizeX
	public String getQRSizeX() {
		return QRSizeX;
	}
	public void setQRSizeX(String QRSizeX) {
		this.QRSizeX = QRSizeX;
	}
	// SizeY
	public String getQRSizeY() {
		return QRSizeY;
	}
	public void setQRSizeY(String QRSizeY) {
		this.QRSizeY = QRSizeY;
	}
	// Pic format
	public String getQRPicFormat() {
		return QRPicFormat;
	}
	public void setQRPicFormat(String QRPicFormat) {
		this.QRPicFormat = QRPicFormat;
	}
	// DestDir
	public String getQRDestDir() {
		return QRDestDir;
	}
	public void setQRDestDir(String QRDestDir) {
		this.QRDestDir = QRDestDir;
	}
	
	
	/**
	 * @return Returns the value.
	 */
	/*
	public ValueMetaAndData getValue()
	{
		return value;
	}
	*/
	/**
	 * @param value The value to set.
	 */
	/*
	public void setValue(ValueMetaAndData value)
	{
		this.value = value;
	}
	*/
	/////////////////////////////////////////////////////////////
	public String getXML()
	{
        final StringBuilder retval = new StringBuilder(1000);

        retval.append("   ").append(XMLHandler.addTagValue("QRColumn", QRColumn)); //$NON-NLS-1$ //$NON-NLS-2$
        retval.append("   ").append(XMLHandler.addTagValue("QRSizeX", QRSizeX)); //$NON-NLS-1$ //$NON-NLS-2$
        retval.append("   ").append(XMLHandler.addTagValue("QRSizeY", QRSizeY)); //$NON-NLS-1$ //$NON-NLS-2$
        retval.append("   ").append(XMLHandler.addTagValue("QRPicFormat", QRPicFormat)); //$NON-NLS-1$ //$NON-NLS-2$
        retval.append("   ").append(XMLHandler.addTagValue("QRDestDir", QRDestDir)); //$NON-NLS-1$ //$NON-NLS-2$
        
        return retval.toString();
	}

	public void getFields(RowMetaInterface r, String origin, RowMetaInterface[] info, StepMeta nextStep, VariableSpace space)
	{
		/*if (value!=null)
		{
			ValueMetaInterface v = value.getValueMeta();
			v.setOrigin(origin);
			
			r.addValueMeta( v );
			*/
		/*
		ValueMetaInterface v = new ValueMeta("RRDFilename", ValueMeta.TYPE_STRING, 44, 0);
		v.setOrigin(origin);
		r.addValueMeta(v);
		
		v = new ValueMeta("RRDDatasource", ValueMeta.TYPE_STRING, 44, 0);
		v.setOrigin(origin);
		r.addValueMeta(v);
		
		v = new ValueMeta("RRDType", ValueMeta.TYPE_STRING, 44, 0);
		v.setOrigin(origin);
		r.addValueMeta(v);
		
		v = new ValueMeta("RRDHeartBeat", ValueMeta.TYPE_STRING, 44, 0);
		v.setOrigin(origin);
		r.addValueMeta(v);
		
		v = new ValueMeta("RRDStartTime", ValueMeta.TYPE_STRING, 44, 0);
		v.setOrigin(origin);
		r.addValueMeta(v);
		
		v = new ValueMeta("RRDMini", ValueMeta.TYPE_STRING, 44, 0);
		v.setOrigin(origin);
		r.addValueMeta(v);
		
		v = new ValueMeta("RRDMaxi", ValueMeta.TYPE_STRING, 44, 0);
		v.setOrigin(origin);
		r.addValueMeta(v);
		*/
	}

	public Object clone()
	{
		Object retval = super.clone();
		return retval;
	}

	public void loadXML(Node stepnode, List<DatabaseMeta> databases, Map<String,Counter> counters)
		throws KettleXMLException
	{
		try
		{	
			setQRColumn(XMLHandler.getTagValue(stepnode, "QRColumn"));
			setQRSizeX(XMLHandler.getTagValue(stepnode, "QRSizeX"));
			setQRSizeY(XMLHandler.getTagValue(stepnode, "QRSizeY"));
			setQRPicFormat(XMLHandler.getTagValue(stepnode, "QRPicFormat"));
			setQRDestDir(XMLHandler.getTagValue(stepnode, "QRDestDir"));
		}
		catch(Exception e)
		{
			throw new KettleXMLException("Unable to read step info from XML node", e);
		}
	}

	public void setDefault()
	{
		QRColumn = "";
		QRSizeX = "128";
		QRSizeY = "128";
	}

	public void readRep(Repository rep, long id_step, List<DatabaseMeta> databases, Map<String,Counter> counters) throws KettleException
	{
		try
		{
			setQRColumn(rep.getStepAttributeString(id_step,0, "QRColumn"));
			setQRSizeX(rep.getStepAttributeString(id_step, "QRSizeX"));
			setQRSizeY(rep.getStepAttributeString(id_step, "QRSizeY"));
			setQRPicFormat(rep.getStepAttributeString(id_step, "QRPicFormat"));
			setQRDestDir(rep.getStepAttributeString(id_step, "QRDestDir"));
		}
		catch(KettleDatabaseException dbe)
		{
			throw new KettleException("error reading step with id_step="+id_step+" from the repository", dbe);
		}
		catch(Exception e)
		{
			throw new KettleException("Unexpected error reading step with id_step="+id_step+" from the repository", e);
		}
	}
	
	public void saveRep(Repository rep, long id_transformation, long id_step) throws KettleException
	{
		try
		{
			rep.saveStepAttribute(id_transformation, id_step, "QRColumn", getQRColumn());
			rep.saveStepAttribute(id_transformation, id_step, "QRSizeX", getQRSizeX());
			rep.saveStepAttribute(id_transformation, id_step, "QRSizeY", getQRSizeY());
			rep.saveStepAttribute(id_transformation, id_step, "QRPicFormat", getQRPicFormat());
			rep.saveStepAttribute(id_transformation, id_step, "QRDestDir", getQRDestDir());
		}
		catch(KettleDatabaseException dbe)
		{
			throw new KettleException("Unable to save step information to the repository, id_step="+id_step, dbe);
		}
	}

	public void check(List<CheckResultInterface> remarks, TransMeta transmeta, StepMeta stepMeta, RowMetaInterface prev, String input[], String output[], RowMetaInterface info)
	{
		CheckResult cr;
		if (prev==null || prev.size()==0)
		{
			cr = new CheckResult(CheckResult.TYPE_RESULT_WARNING, "Not receiving any fields from previous steps!", stepMeta);
			remarks.add(cr);
		}
		else
		{
			cr = new CheckResult(CheckResult.TYPE_RESULT_OK, "Step is connected to previous one, receiving "+prev.size()+" fields", stepMeta);
			remarks.add(cr);
		}
		
		// See if we have input streams leading to this step!
		if (input.length>0)
		{
			cr = new CheckResult(CheckResult.TYPE_RESULT_OK, "Step is receiving info from other steps.", stepMeta);
			remarks.add(cr);
		}
		else
		{
			cr = new CheckResult(CheckResult.TYPE_RESULT_ERROR, "No input received from other steps!", stepMeta);
			remarks.add(cr);
		}
	}
	
	public StepDialogInterface getDialog(Shell shell, StepMetaInterface meta, TransMeta transMeta, String name)
	{
		return new QRSendPluginDialog(shell, meta, transMeta, name);
	}

	public StepInterface getStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int cnr, TransMeta transMeta, Trans disp)
	{
		return new QRSendPlugin(stepMeta, stepDataInterface, cnr, transMeta, disp);
	}

	public StepDataInterface getStepData()
	{
		return new QRSendPluginData();
	}
}
