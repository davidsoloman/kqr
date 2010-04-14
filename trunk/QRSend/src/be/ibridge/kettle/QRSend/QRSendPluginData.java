/*
 *
 *
 */

package be.ibridge.kettle.QRSend;

import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;

/**
 * 
 * 
 * @author Matt
 * @since  24-mrt-2005
 */
public class QRSendPluginData extends BaseStepData implements StepDataInterface
{
	public RowMetaInterface outputRowMeta;

    public QRSendPluginData()
	{
		super();
	}
}
