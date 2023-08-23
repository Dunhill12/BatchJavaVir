package batch.main;

import batch.entities.Virement;
import batch.utile.Constante;
import batch.utile.DbConnexion;
import batch.utile.GenrationFichier;
import batch.utile.utility;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

public class Main {

    public static Logger logger = Logger.getLogger(Main.class.getName());
    public static EntityManager emOracleVir;
    public static EntityManager emOracleFic;
    public static String virAmb = "";
    public static int randNum = 0;



    public static void main(String[] args) {
            logger.info("#########################################################################");
            logger.info("################                DEBUT BATCH           ###################");
            logger.info("#########################################################################");
            try {
                String dateajd = utility.dateToString(new Date(), "yyMMdd");
                if(!new File("./"+Constante.FILE_SEPA+Constante.REP_OUT_SWIFT+Constante.FILE_SEPA+dateajd).exists()) {
                    new File("./"+Constante.FILE_SEPA+Constante.REP_OUT_SWIFT+Constante.FILE_SEPA+dateajd).mkdirs();
                }
                emOracleVir = DbConnexion.getEntityManagerVirOra().createEntityManager();
                emOracleFic = DbConnexion.getEntityManagerFicOra().createEntityManager();
                String reqVir = "select * from VIR_AMBASSADE_AS where etat = 'A'";
                Query queryVir = emOracleVir.createNativeQuery(reqVir, Virement.class);


                List<Virement> listVir = null;
                try {
                    listVir = queryVir.getResultList();
                } catch (NoResultException e) {
                    listVir = null;
                }
                logger.info("################        LIST VIREMENT AMBASSADE       ###################");
                logger.info("################        CONTITENT : "+listVir.size()+" VIREMENTS       ###################");
                logger.info("##################### DEBUT GENERATION FICHER VIREMENT ##################");
                for(Virement vir : listVir) {
                    if(vir != null){
                        Query ficQuery = emOracleFic.createNativeQuery("update fic_ambassade set nom ='TESTING' ");
                        String dirFile = "./"+Constante.FILE_SEPA+Constante.REP_OUT_SWIFT+Constante.FILE_SEPA+dateajd+Constante.FILE_SEPA+dateajd+ficQuery.getResultList().get(1)+".OUT";
                        ficQuery.executeUpdate();
                        GenrationFichier fichier = new GenrationFichier();
                        virAmb += fichier.genereFileCorp(vir);
                        utility.stringToFile(virAmb, dirFile);
                        virAmb = "";
                    };
                }

            } catch (Exception e) {
                logger.error("Erreur lors de la recuperation des virements", e);
            } finally {
                logger.info("#########################################################################");
                logger.info("################                FIN BATCH             ###################");
                logger.info("#########################################################################");
                System.exit(0);
            }



        }
}
