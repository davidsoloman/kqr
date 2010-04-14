
package be.ibridge.kettle.QRSend;


import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStep;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;

public class QRSendPlugin extends BaseStep implements StepInterface
{
    private QRSendPluginData data;
	private QRSendPluginMeta meta;
	
	public QRSendPlugin(StepMeta s, StepDataInterface stepDataInterface, int c, TransMeta t, Trans dis)
	{
		super(s,stepDataInterface,c,t,dis);
	}
	
	public boolean processRow(StepMetaInterface smi, StepDataInterface sdi) throws KettleException
	{
	    meta = (QRSendPluginMeta)smi;
	    data = (QRSendPluginData)sdi;
	    
		Object[] r=getRow();    // get row, blocks when needed!
		if (r==null)  // no more input to be expected...
		{
			setOutputDone();
			return false;
		}
        
        if (first)
        {
            first = false;
            data.outputRowMeta = (RowMetaInterface)getInputRowMeta().clone();
            meta.getFields(data.outputRowMeta, getStepname(), null, null, this);      
        }

        int QRSizex = Integer.parseInt(meta.getQRSizeX());
        int QRSizey = Integer.parseInt(meta.getQRSizeY());
        String QRPicType = meta.getQRPicFormat();
        String QRDest1 = meta.getQRDestDir();

        // Permet de retrouver la position d'un champs !!!
        int ColPos = data.outputRowMeta.indexOfValue(meta.getQRColumn());
        logBasic ("The plugin is encoding value : [" + r[ColPos] + "] from column [" + meta.getQRColumn()+"].");
        logBasic ("Picture format : " + QRPicType);
        EncodeQRCode(r[ColPos].toString(), QRSizex, QRSizey, QRPicType, QRDest1, linesRead);
        
        
		if (checkFeedback(linesRead)) logBasic("Linenr "+linesRead);  // Some basic logging every 5000 rows.
			
		return true;
	}

	public void EncodeQRCode(String qRCol, int qRSizex, int qRSizey,
		String qRPicType, String qRDest1, long LinesRead) {

		ByteMatrix matrix; 
		com.google.zxing.Writer writer = new QRCodeWriter(); 
		try { 
		 matrix = writer.encode(qRCol, com.google.zxing.BarcodeFormat.QR_CODE, qRSizex, qRSizey); 
		} 
		catch (com.google.zxing.WriterException e) { 
		 return; 
		} 

		int width = matrix.getWidth();  
		int height = matrix.getHeight();  
		 
		byte[][] array = matrix.getArray(); 
		 
		//create buffered image to draw to 
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
		 
		//iterate through the matrix and draw the pixels to the image 
		for (int y = 0; y < height; y++) {  
		 for (int x = 0; x < width; x++) {  
		  int grayValue = array[y][x] & 0xff;  
		  image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF)); 
		 } 
		} 
		//write the image to the output stream 
		File file = new File(qRDest1 + "QRCode" + LinesRead+"." + qRPicType);
		try {
			ImageIO.write(image, qRPicType, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	

	public boolean init(StepMetaInterface smi, StepDataInterface sdi)
	{
	    meta = (QRSendPluginMeta)smi;
	    data = (QRSendPluginData)sdi;

	    return super.init(smi, sdi);
	}

	public void dispose(StepMetaInterface smi, StepDataInterface sdi)
	{
	    meta = (QRSendPluginMeta)smi;
	    data = (QRSendPluginData)sdi;

	    super.dispose(smi, sdi);
	}
	
	//
	// Run is were the action happens!
	public void run()
	{
		logBasic("Starting to run...");
		try
		{
			while (processRow(meta, data) && !isStopped());
		}
		catch(Exception e)
		{
			logError("Unexpected error : "+e.toString());
            logError(Const.getStackTracker(e));
			setErrors(1);
			stopAll();
		}
		finally
		{
		    dispose(meta, data);
			logBasic("Finished, processing "+linesRead+" rows");
			markStop();
		}
	}
}
