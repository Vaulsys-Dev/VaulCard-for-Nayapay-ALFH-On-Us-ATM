package vaulsys.protocols.saderat87;

import vaulsys.protocols.base.ProtocolDialog;
import vaulsys.protocols.base.ProtocolMessage;
import vaulsys.protocols.ifx.imp.Ifx;
import vaulsys.protocols.PaymentSchemes.ISO8583.base.ISOMsg;
import vaulsys.protocols.saderat87.Saderat87ProtocolDialog;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class Saderat87ProtocolDialog implements ProtocolDialog {

    transient Logger logger = Logger.getLogger(Saderat87ProtocolDialog.class);

    @Override
    public Ifx refine(Ifx ifx) {
        return ifx;
    }

    @Override
    public ProtocolMessage refine(ProtocolMessage protocolMessage) {

        ISOMsg isoMsg = (ISOMsg) protocolMessage;

        int[] msg200 = new int[]{2, 3, 4, 6, 7, 11, 12, 13, 14, 17, 22, 25, 32, 33, 35, 37, 41, 42, 43, 48, 49, 52, 98, 103, 121};
        int[] msg400 = new int[]{2, 3, 4, 6, 7, 11, 12, 13, 17, 25, 32, 33, 35, 37, 39, 41, 42, 43, 48, 49, 90, 95, 98, 103};
        int[] msg100 = new int[]{2, 3, 7,/*10,*/11, 12, 13, 15, 17,/*18,*/25, 32, 33, 37, 41, 42, 48, /*51,*/100/*,64*/};
        int[] msg210 = new int[]{2, 3, 4, 6, 7, 11, 12, 13, 15, 32, 33, 35, 37, 38, 39, 41, 43, 44, 49, 51, 54, 100/*,64*/};
        int[] msg410 = new int[]{2, 3, 4, 6, 7, 11, 12, 13, 15, 32, 33, 37, 39, 42, 48, 51, 54/*,64*/, 100};
        int[] msg800 = new int[]{7,11,15,32,33, 48, 53, 70, 96, 128};
        int[] msg810 = new int[]{7,11,15,32,33, 39, 48, 70, 96, 128};
        int[] msg500 = new int[]{7, 11, 15, 17, 32, 33, 50, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 97, 99, 124};
        
        int[] msg = null;

        try {
            String mtiStr = isoMsg.getMTI();
            Integer mti = Integer.parseInt(mtiStr);
            switch (mti) {
            	case 100:
            	case 101:
            		msg = msg100;
            		break;
                case 200:
                case 201:
                    msg = msg200;
                    break;
                case 210:
                case 211:
                    msg = msg210;
                    break;
                case 400:
                case 420:
                    msg = msg400;
                    break;
                case 410:
                case 430:
                    msg = msg410;
                    break;
                case 800:
                case 820:
                	msg = msg800;
                	break;
                case 810:
                case 830:
                	msg = msg810;
                	break;
                case 500:
                case 502:
                case 520:
                case 522:
                	msg = msg500;
                default:
                    break;
            }

            ArrayList<Integer> removedFields = new ArrayList<Integer>();
            ArrayList<Integer> neededFields = new ArrayList<Integer>();

            int k = 0; //
            for (int i = 2; i < 64; i++) { //field counter
                if (isoMsg.hasField(i) && (k >= msg.length || msg[k] != i)) {//msg has fld i but msg says no
                    //logger.error("Message has field " + i + " but msg says no.");
                    isoMsg.unset(i);//unset fld i
                    removedFields.add(i);
                }
                if (!isoMsg.hasField(i) && (k < msg.length && msg[k] == i)) {
                    //msg does not have fld i but msg says yes
                    boolean result = setField(isoMsg, i);//set fld i
                    neededFields.add(i);
                }
                
                if (!isoMsg.hasField(i) && (k >= msg.length || msg[k] != i)) {//msg does not have fld i and msg says no
                } else {//msg has fld i and msg says yes
                    k++;
                }
            }

            int i = 90;
            if (isoMsg.hasField(i) && (k >= msg.length || msg[k] != i)) {//msg has fld i but msg says no
                //logger.error("Message has field " + i + " but msg says no.");
                isoMsg.unset(i);//unset fld i
                removedFields.add(i);
            }
            if (!isoMsg.hasField(i) && (k < msg.length && msg[k] == i)) {
                //msg does not have fld i but msg says yes
                boolean result = setField(isoMsg, i);//set fld i
                neededFields.add(i);
            }
            
            if (!isoMsg.hasField(i) && (k >= msg.length || msg[k] != i)) {//msg does not have fld i and msg says no
            } else {//msg has fld i and msg says yes
                k++;
            }
            
            i = 95;
            if (isoMsg.hasField(i) && (k >= msg.length || msg[k] != i)) {//msg has fld i but msg says no
                //logger.error("Message has field " + i + " but msg says no.");
                isoMsg.unset(i);//unset fld i
                removedFields.add(i);
            }
            if (!isoMsg.hasField(i) && (k < msg.length && msg[k] == i)) {
                //msg does not have fld i but msg says yes
                boolean result = setField(isoMsg, i);//set fld i
                neededFields.add(i);
            }
            
            if (!isoMsg.hasField(i) && (k >= msg.length || msg[k] != i)) {//msg does not have fld i and msg says no
            } else {//msg has fld i and msg says yes
                k++;
            }
                
            i = 100;
            if (isoMsg.hasField(i) && (k >= msg.length || msg[k] != i)) {//msg has fld i but msg says no
            	logger.error("Message has field " + i + " but msg says no. msg.length "+msg.length+" K:"+k+"msg[k]:"+msg[k]);

            	isoMsg.unset(i);//unset fld i
            	removedFields.add(i);
            }
            if (!isoMsg.hasField(i) && (k < msg.length && msg[k] == i)) {
            	//msg does not have fld i but msg says yes
            	boolean result = setField(isoMsg, i);//set fld i
            	neededFields.add(i);
            }
            
            if (!isoMsg.hasField(i) && (k >= msg.length || msg[k] != i)) {//msg does not have fld i and msg says no
            } else {//msg has fld i and msg says yes
            	k++;
            }
            
            if (neededFields.size() != 0)
                logger.warn("Message doesn't have fields " + neededFields.toString() + " but it should have. Switch didn't add anything.");
            if (removedFields.size() != 0)
                logger.warn("Message does    have fields " + removedFields.toString() + " but it should not. Switch removed them.");

            return protocolMessage;
        } catch (Exception ex) {
            if (true)
                return null;
        }

        return null;

    }

    private boolean setField(ISOMsg msg, int fldno) throws Exception {

        return false;
        /*
          switch (fldno) {
          case 10:
              msg.set(fldno, "1");
              return true;
          case 49:
          case 51:
              msg.set(fldno, "364");
              return true;
          case 2:
          case 3:
          case 11:
          case 41:
              return false;
              //throw new Exception("Fatal Error in refining ISOMsg.");
          case 96:
              msg.set(fldno, "00000000000000000000000000000000");
              return true;
          case 4:
              msg.set(fldno, "2");
              return true;
          default:
              msg.set(fldno, "0");
              return false;
          }
          */
    }

    ////Raza Adding for Field traslation start
    @Override
    public ProtocolMessage TranslateToFanap(ProtocolMessage protocolMessage) throws Exception
    {
        //logger.info("Translating incoming message from Saderat...");
        return protocolMessage;
    }

    @Override
    public ProtocolMessage TranslateFromFanap(ProtocolMessage protocolMessage) throws Exception
    {
        //logger.info("Translating outgoing message for Saderat...");
        return protocolMessage;
    }
    ////Raza Adding for Field traslation end

}