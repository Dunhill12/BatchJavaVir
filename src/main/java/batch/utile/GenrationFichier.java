package batch.utile;

import batch.entities.Virement;
import batch.main.Main;
import java.util.Date;
import java.util.UUID;

public class GenrationFichier {
	public static String genereFileCorp(Virement bean) {
		
		StringBuilder str = new StringBuilder();
		UUID u = UETRGenerator.generateType1UUID();
		String dateajd = utility.dateToString(new Date(), "yyMMdd");
			str.append("{1:xxxxxxxxxxxxxx}{2:xxxxxxxxxxxxxxxxxxx}{3:{121:"+u+"}}{4:" +
					"\n"+
					":20:SAL"+utility.sDateToString(dateajd, "ddMMyy")+ Main.randNum+
					"\n"+
					":23B:"+bean.getSens()+
					"\n" +
					":32A:"+dateajd+bean.getDevise()+bean.getMontant()+
					"\n" +
					":50A:/xxxxxxxxxx"+
					"\n" +
					"xxxxxxxxxxxxxxxx" +
					"\n" +
					"xxxxxxxxxxxxxx" +
					"\n"+
					"xxxxxxxxxxxxxxx" +
					"\n"+
					":59A:/"+bean.getCpt()+
					"\n"+
					bean.getNom()+
					"\n"+
					bean.getBanque()+
							"\n" +
							"-}{5:{MAC:xxxxxxxxxxxxxx}{CHK:xxxxxxxxxx}{PDE:}}{S:{SAC:}{COP:P}}"
					);

		//str.append(utility.trancOrCompleteWithBlanc("|  "+utility.trancOrCompleteWithBlancCorps(bean.getId(),3)+"   |"+utility.trancOrCompleteWithBlancCorps(bean.getCpt(),18)+"|"+utility.trancOrCompleteWithBlancCorps(bean.getCodeBanque(),17)+"|"+utility.trancOrCompleteWithBlancCorps(bean.getNom(),18)+"|"+utility.trancOrCompleteWithBlancCorps(bean.getMontant(),17)+"|"+utility.trancOrCompleteWithBlancCorps(bean.getBanque(),17)+"|"+utility.trancOrCompleteWithBlancCorps(bean.getSens(), 17)+"|"+utility.trancOrCompleteWithBlancCorps(bean.getEtat(), 17)+"|"+utility.trancOrCompleteWithBlancCorps(bean.getDateDernierVirement(), 17)+"|"+utility.trancOrCompleteWithBlancCorps(bean.getDateSys(), 17)+"|"+utility.trancOrCompleteWithBlancCorps(bean.getDevise(), 17)+"|"+utility.trancOrCompleteWithBlancCorps(bean.getTier(),17), 10)+"\n");
		//str.append(utility.trancOrCompleteWithBlanc("+-------------+---------------------+--------------------------+---------------------------------+-----------------------------------+-----------------------------------+-----------------------------------+-----------------------------------+-----------------------------------+-----------------------------------+-----------------------------------+-----------------------------------+", 10)+"\n");
		return str.toString();
	}
	
	

}
