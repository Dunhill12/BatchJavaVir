package batch.utile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DbConnexion {
    private static final String PERSISTENCE_SERV_INF = "DS_VIR_AMBASSADE_AS";

    private static final String PERSISTENCE_SERV_FIC = "DS_FIC_AMBASSADE";
    private static EntityManagerFactory factoryDsVirInf;
    private static EntityManagerFactory factoryDsFicInf;

    public static EntityManagerFactory getEntityManagerVirOra(){
        if (factoryDsVirInf==null){
            factoryDsVirInf = javax.persistence.Persistence.createEntityManagerFactory(PERSISTENCE_SERV_INF);
        }
        return factoryDsVirInf;
    }

    public static EntityManagerFactory getEntityManagerFicOra(){
        if (factoryDsFicInf==null){
            factoryDsFicInf = javax.persistence.Persistence.createEntityManagerFactory(PERSISTENCE_SERV_FIC);
        }
        return factoryDsFicInf;
    }

}
