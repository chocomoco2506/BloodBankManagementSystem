import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class BloodBankServer {
    public static void main(String args[]) {
        try {
            // Create and initialize the ORB
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // Create the server implementation
            BloodBankServerImpl bloodBankServerImpl = new BloodBankServerImpl();
            bloodBankServerImpl.setORB(orb);

            // Get the CORBA object reference
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(bloodBankServerImpl);
            BloodBank.BloodBankServer href = BloodBank.BloodBankServerHelper.narrow(ref);

            // Register the server with the naming service
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            org.omg.CosNaming.NamingContextExt ncRef = org.omg.CosNaming.NamingContextExtHelper.narrow(objRef);
            org.omg.CosNaming.NameComponent path[] = ncRef.to_name("BloodBankServer");
            ncRef.rebind(path, href);

            // Wait for invocations from clients
            System.out.println("BloodBankServer ready and waiting...");
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
