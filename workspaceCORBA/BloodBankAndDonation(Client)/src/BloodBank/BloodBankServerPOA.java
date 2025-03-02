package BloodBank;


/**
* BloodBank/BloodBankServerPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from BloodBankServer.idl
* Friday, August 2, 2024 11:25:27 PM MMT
*/

public abstract class BloodBankServerPOA extends org.omg.PortableServer.Servant
 implements BloodBank.BloodBankServerOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("bloodsearch", new java.lang.Integer (0));
    _methods.put ("nrc_check", new java.lang.Integer (1));
    _methods.put ("getInventory", new java.lang.Integer (2));
    _methods.put ("getInventoryHistory", new java.lang.Integer (3));
    _methods.put ("getDonationHistory", new java.lang.Integer (4));
    _methods.put ("bloodrequest", new java.lang.Integer (5));
    _methods.put ("blooddonate", new java.lang.Integer (6));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // BloodBank/BloodBankServer/bloodsearch
       {
         String bloodtype = in.read_string ();
         String $result[] = null;
         $result = this.bloodsearch (bloodtype);
         out = $rh.createReply();
         BloodBank.BloodBankServerPackage.DonorInfoHelper.write (out, $result);
         break;
       }

       case 1:  // BloodBank/BloodBankServer/nrc_check
       {
         String donor_nrc = in.read_string ();
         String $result[] = null;
         $result = this.nrc_check (donor_nrc);
         out = $rh.createReply();
         BloodBank.BloodBankServerPackage.DonorHelper.write (out, $result);
         break;
       }

       case 2:  // BloodBank/BloodBankServer/getInventory
       {
         String $result[] = null;
         $result = this.getInventory ();
         out = $rh.createReply();
         BloodBank.BloodBankServerPackage.inventoryHelper.write (out, $result);
         break;
       }

       case 3:  // BloodBank/BloodBankServer/getInventoryHistory
       {
         String $result[] = null;
         $result = this.getInventoryHistory ();
         out = $rh.createReply();
         BloodBank.BloodBankServerPackage.historyHelper.write (out, $result);
         break;
       }

       case 4:  // BloodBank/BloodBankServer/getDonationHistory
       {
         String donor_nrc = in.read_string ();
         String $result = null;
         $result = this.getDonationHistory (donor_nrc);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 5:  // BloodBank/BloodBankServer/bloodrequest
       {
         String blood_id = in.read_string ();
         String receiver_name = in.read_string ();
         String receiver_phone = in.read_string ();
         String receive_date = in.read_string ();
         String receiver_age = in.read_string ();
         String receiver_gender = in.read_string ();
         String receiver_address = in.read_string ();
         String receiver_email = in.read_string ();
         String $result = null;
         $result = this.bloodrequest (blood_id, receiver_name, receiver_phone, receive_date, receiver_age, receiver_gender, receiver_address, receiver_email);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 6:  // BloodBank/BloodBankServer/blooddonate
       {
         String bloodtype = in.read_string ();
         String donor_name = in.read_string ();
         String donor_phone = in.read_string ();
         String donation_date = in.read_string ();
         String donor_age = in.read_string ();
         String donor_gender = in.read_string ();
         String donor_address = in.read_string ();
         String donor_email = in.read_string ();
         String donor_nrc = in.read_string ();
         String $result = null;
         $result = this.blooddonate (bloodtype, donor_name, donor_phone, donation_date, donor_age, donor_gender, donor_address, donor_email, donor_nrc);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:BloodBank/BloodBankServer:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public BloodBankServer _this() 
  {
    return BloodBankServerHelper.narrow(
    super._this_object());
  }

  public BloodBankServer _this(org.omg.CORBA.ORB orb) 
  {
    return BloodBankServerHelper.narrow(
    super._this_object(orb));
  }


} // class BloodBankServerPOA
