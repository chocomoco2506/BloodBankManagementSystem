package BloodBank.BloodBankServerPackage;


/**
* BloodBank/BloodBankServerPackage/DonorHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from BloodBankServer.idl
* Friday, August 2, 2024 11:25:27 PM MMT
*/

public final class DonorHolder implements org.omg.CORBA.portable.Streamable
{
  public String value[] = null;

  public DonorHolder ()
  {
  }

  public DonorHolder (String[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = BloodBank.BloodBankServerPackage.DonorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    BloodBank.BloodBankServerPackage.DonorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return BloodBank.BloodBankServerPackage.DonorHelper.type ();
  }

}
