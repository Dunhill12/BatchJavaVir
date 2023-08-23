package batch.utile;

//import ma.eai.etranger.entities.ifx2.CommissionBam;


import javax.persistence.EntityManager;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import ma.eai.midw.log.Log;

//import org.joda.time.DateTime;
//import org.joda.time.Months;

/**
 * 
 *  
 * 
 */
public class utility {

	public static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public static String getBeanContents(Object o) {
		if (o == null) {
			return "bean==null";
		}
		Method f[] = o.getClass().getDeclaredMethods();
		String res = " \n # Object Name : " + o.getClass().getName() + " #\n";
		for (int i = 0; i < f.length; i++) {
			String name = f[i].getName();
			if (name.substring(0, 3).equals("get")) {
				try {
					res += "\n - " + name.substring(3, name.length()).toLowerCase();
					res += " : " + f[i].invoke(o, null);
				} catch (IllegalArgumentException e) {
					//
					// e.printStackTrace();
				} catch (IllegalAccessException e) {
					//
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					//
					e.printStackTrace();
				}
			}
		}
		return res;
	}

	public static boolean copyFile(File source, File dest) {
		try {
			// Declaration et ouverture des flux
			java.io.FileInputStream sourceFile = new java.io.FileInputStream(source);

			try {
				FileOutputStream destinationFile = null;

				try {
					destinationFile = new FileOutputStream(dest);

					// Lecture par segment de 0.5Mo
					byte buffer[] = new byte[512 * 1024];
					int nbLecture;

					while ((nbLecture = sourceFile.read(buffer)) != -1) {
						destinationFile.write(buffer, 0, nbLecture);
					}
				} finally {
					destinationFile.close();
				}
			} finally {
				sourceFile.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false; // Erreur
		}

		return true; // Rsultat OK
	}

	public static boolean moveFile(File source, File destination) {
		if (!destination.exists()) {
			// On essaye avec renameTo
			boolean result = source.renameTo(destination);
			if (!result) {
				// On essaye de copier
				result = true;
				result &= copyFile(source, destination);
				if (result)
					result &= source.delete();

			}
			return (result);
		} else {
			// Si le fichier destination existe, on annule ...
			return (false);
		}
	}

	public static Date stringToDate(String sDate) throws ParseException {
		return formatter.parse(sDate);
	}

	public static Date stringToDate(String sDate, String Patern) throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat(Patern);
		return f.parse(sDate);
	}

	public static Timestamp toDBDateFormat(String sDate) throws ParseException {
		return new Timestamp(stringToDate(sDate).getTime());
	}

	public static String dateToString(Date date) {
		return formatter.format(date);
	}

	public static String dateToString(Date date, String patern) {
		formatter = new SimpleDateFormat(patern);
		return formatter.format(date);
	}
	public static String sDateToString(String date, String patern){
		try {
			return dateToString(stringToDate(date), patern);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date addDay(Date d, int number) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d);
		c1.add(Calendar.DATE, number);
		return c1.getTime();
	}

	public static Date addMonths(Date d, int number) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d);
		c1.add(Calendar.MONTH, number);
		return c1.getTime();
	}

	public static Date addyears(Date d, int number) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d);
		c1.add(Calendar.YEAR, number);
		return c1.getTime();
	}

	public static File stringToFile(String str, String path) throws IOException {

		File file = new File(path);
		FileWriter fileWriter = new FileWriter(file);
		try {
			fileWriter.write(str);
			fileWriter.flush();
			return file;
		} finally {
			fileWriter.close();
		}

	}
	
	public static void stringToFileTest(String str, String path) throws IOException {

		File file = new File(path);
		FileWriter fileWriter = new FileWriter(file);
		try {
			fileWriter.write(str);
			fileWriter.flush();
			
		} finally {
			fileWriter.close();
		}

	}

	public static String trancOrCompleteWithBlanc(String str, int longueur) {
		if (str == null) {
			str = "0.0";
		}

		for (int i = 0; i < longueur; i++) {
			str = " ".concat(str);
		}
		return str;

	}

	public static String fileToString(File file) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(Constante.LINE_SEPAR);
			}

			return stringBuilder.toString();
		} finally {
			reader.close();
		}

	}

//	public static void traitemantFileCommission(File file, EntityManager emOracleEtr, EntityManager emOracleTi,
//			EntityManager emInformixEtr) throws IOException {
//		try {
//			String str = fileToString(file);
//			String[] line = str.split("\n");
//			List<CommissionBam> listComm = new ArrayList<CommissionBam>();
//			int j = 0;
//			for (int i = 1; i < line.length; i++) {
//				String[] champs = line[i].split("\\|");
//
//				if (null != champs[4] && !champs[4].trim().equals("") && null != champs[5] && !champs[5].trim().equals("")) {
//					CommissionBam comm = new CommissionBam();
//					comm.setType(getType(champs[4].toString()));
//					comm.setReference(getReference(champs[4].toString()));
//					comm.setLibOperation(champs[5].toString());
//					comm.setDateOperation(champs[7].toString());
//					comm.setDateValeur(champs[8].toString());
//
//					if (null != champs[10] && !champs[10].trim().equals("")) {
//						comm.setMntCredit(new BigDecimal(champs[10].toString().replace(",", ".")));
//					} else {
//						comm.setMntCredit(new BigDecimal("0.0"));
//					}
//
//					if (null != champs[9] && !champs[9].trim().equals("")) {
//						comm.setMntDebit(new BigDecimal(champs[9].toString().replace(",", ".")));
//					} else {
//						comm.setMntDebit(new BigDecimal("0.0"));
//					}
//
//					comm.setDevise(getDevise(comm.getReference(), comm.getLibOperation(), emOracleTi, emInformixEtr));
//					listComm.add(comm);
//				} else {
//					System.out.println(">>>> REFERENCE NULL POUR LA LIGNE " + i);
//				}
//
//			}
//
//			if (null != listComm && listComm.size() > 0) {
//				emOracleEtr.getTransaction().begin();
//				for (CommissionBam comB : listComm) {
//					emOracleEtr.persist(comB);
//				}
//				emOracleEtr.getTransaction().commit();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public static String getReference(String ref) {

		try {
			String reference = "";

			String lettre = ref.substring(2, 3);

			if (lettre.equals("I")) {// ECC I
				reference = "ECC" + ref;
			} else if (lettre.equals("H")) {// EDC H
				reference = "EDC" + ref;
			} else if (lettre.equals("B")) {// ELC B
				reference = "ELC" + ref;
			} else if (lettre.equals("L")) {// FIM L
				reference = "FIM" + ref;
			} else if (lettre.equals("C")) {// GRE C
				reference = "GRE" + ref;
			} else if (lettre.equals("D")) {// GRR D
				reference = "GRR" + ref;
			} else if (lettre.equals("F")) {// ICC F
				reference = "ICC" + ref;
			} else if (lettre.equals("E")) {// IDC E
				reference = "IDC" + ref;
			} else if (lettre.equals("A")) {// ILC A
				reference = "ILC" + ref;
			} else if (lettre.equals("K")) {// RPT K
				reference = "RPT" + ref;
			} else if (lettre.equals("Q")) {// TRC Q
				reference = "TRC" + ref;
			} else if (lettre.equals("J")) {// TRF J
				reference = "TRF" + ref;
			} else if (lettre.equals("R")) {// TRP R
				reference = "TRP" + ref;
			} else
				reference = ref;

			return reference;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String getDevise(String ref, String libOp, EntityManager emOracleTi, EntityManager emInformixEtr) {

		try {
			
			String reference = null;
			String type = null;

			Pattern r = Pattern.compile("(VD|AD)([0-9]{12})");
			Matcher m = r.matcher(libOp);
			
			while (m.find()) {
				type = m.group(1);
				reference = m.group(2);
			}
			
		

			String devise = null;
			String reqInformixVente = "select distinct vte_coddev||'' from historique_vente where vte_numref = '"+reference+"'";
			String reqInformixAchat = "select distinct ach_coddev||'' from historique_achat where ach_numref = '"+reference+"'";
			String reqTiTR = "Select cast(ccy as varchar2(3)) from tizone1.master where  master_ref = '"+ref+"'";
			String reqTi = "Select cast(pay_ccy as varchar2(3)) from tizone1.cpaymaster where key97 in (Select key97 from tizone1.master where master_ref = '"+ref+"')";

			
			if (null != reference && !reference.trim().equals("")) {
				if (type.equals("AD")) {
					System.out.println(">>>>>>  REQ  VENTE INFORMIX : " + reqInformixVente);
					devise = ""+emInformixEtr.createNativeQuery(reqInformixVente).getSingleResult();
				}
				if (type.equals("VD")) {
					System.out.println(">>>>>>  REQ ACHAT INFORMIX : " + reqInformixAchat);
					devise = ""+emInformixEtr.createNativeQuery(reqInformixAchat).getSingleResult();
				}
			} else {
				if (!ref.startsWith("TR")) {
					System.out.println(">>>>>>  REQ TR TI : " + reqTiTR);
					devise = (String) emOracleTi.createNativeQuery(reqTiTR).getSingleResult();
				} else {
					System.out.println(">>>>>>  REQ NON TR TI : " + reqTi);
					devise = (String) emOracleTi.createNativeQuery(reqTi).getSingleResult();
				}
			}
			
			System.out.println(">>>>> LIB OP : "+libOp+" >>>> TYPE OP :"+type+" >>>> OPERATION  : "+ref+" >>> REF  : "+reference+" - "+devise);
			return devise;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(">>>>>> PAS DE RESULTAT POUR LA REFERENCE : " + ref);
			return null;
		}

	}

	public static String getType(String ref) {

		try {
			String type = "";

			String lettre = ref.substring(2, 3);

			if (lettre.equals("I")) {// ECC I
				type = "V";
			} else if (lettre.equals("H")) {// EDC H
				type = "V";
			} else if (lettre.equals("B")) {// ELC B
				type = "V";
			} else if (lettre.equals("L")) {// FIM L
				type = "A";
			} else if (lettre.equals("C")) {// GRE C
				type = "A";
			} else if (lettre.equals("D")) {// GRR D
				type = "A";
			} else if (lettre.equals("F")) {// ICC F
				type = "A";
			} else if (lettre.equals("E")) {// IDC E
				type = "A";
			} else if (lettre.equals("A")) {// ILC A
				type = "A";
			} else if (lettre.equals("K")) {// RPT K
				type = "V";
			} else if (lettre.equals("Q")) {// TRC Q
				type = "A";
			} else if (lettre.equals("J")) {// TRF J
				type = "A";
			} else if (lettre.equals("R")) {// TRP R
				type = "A";
			} else
				type = "V";

			return type;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String trancOrCompleteWithBlancCorps(String str, int longueur) {
		if (str == null || str.trim().equals("")) {
			str = "0.0";
		}

		str = str != null ? str : "";
		if (str.length() >= longueur)
			return str.substring(0, longueur);
		else {
			String tmp = str;
			for (int i = 0; i < longueur - str.length(); i++)
				tmp = " ".concat(tmp);
			return tmp;
		}
	}

	public static String FormateDecimale(int ref, String Pattern) {
		DecimalFormat df = (DecimalFormat) DecimalFormat.getNumberInstance();
		df.applyPattern(Pattern);
		String reference = df.format(ref);
		return reference;
	}

	/*
	 * Parse la date en String selon le format donn�
	 * 
	 */
	public static String DateToString(Date d, String frt) throws ParseException {

		DateFormat formatter;
		formatter = new SimpleDateFormat(frt);
		String s = formatter.format(d);

		return s;
	}

	public static Date addWorkingDays(Date aDate, int n) {

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(aDate);

		int offset = 1;

		int offsetSaturday = 2;

		int offsetFriday = 3;

		for (int i = 0; i < n; i++) {
			if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
				calendar.add(Calendar.DATE, offsetFriday);
			} else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
				calendar.add(Calendar.DATE, offsetSaturday);
			} else {
				calendar.add(Calendar.DATE, offset);
			}
		}
		return calendar.getTime();
	}

	public static String completWith0(int a) {
		String b = a + "";
		if (b.length() == 1) {
			b = "00" + a;
		}
		if (b.length() == 2) {
			b = "0" + a;
		}
		return b;
	}

	public static String trancOrCompleteWith0(int number, int longueur) {
		String patern = "";
		for (int i = 0; i < longueur; i++) {
			patern = patern + "0";
		}
		System.out.println(patern);
		NumberFormat formatter = new DecimalFormat("000000");
		return formatter.format(number);
	}

	public static String getMois(String mois) {
		String name = "";

		if (mois.equals("01"))
			name = "Janvier";

		if (mois.equals("02"))
			name = "Février";

		if (mois.equals("03"))
			name = "Mars";

		if (mois.equals("04"))
			name = "Avril";

		if (mois.equals("05"))
			name = "Mai";

		if (mois.equals("06"))
			name = "Juin";

		if (mois.equals("07"))
			name = "Juillet";

		if (mois.equals("08"))
			name = "Aout";

		if (mois.equals("09"))
			name = "septembre";

		if (mois.equals("10"))
			name = "Octobre";

		if (mois.equals("11"))
			name = "Novembre";

		if (mois.equals("12"))
			name = "Décembre";

		return name;
	}

	public static String FormateDecimale(double ref, String Pattern) {

		DecimalFormat df = (DecimalFormat) DecimalFormat.getNumberInstance();
		df.applyPattern(Pattern);

		return df.format(ref);

	}

	public static String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

	// public static void DisplayExceptionDetail(Exception e , String MethodName){
	// Log.info("###########################################################################");
	// Log.info("############################# EXCEPTION : "+MethodName+"
	// ###########################".toUpperCase());
	// Log.info("###########################################################################");
	// Log.info("Exception MESSAGE : " + e.getMessage() + " ");
	// Log.info("Exception CAUSE : " + e.getCause() + " ");
	// Log.info("Exception StackTrace : " + utility.getStackTrace(e) + " ");
	// Log.info("###########################################################################");
	// e.printStackTrace();
	// }

	public static boolean supprimerFichier(String nomFichierIN) {
		File file = new File(nomFichierIN);
		return file.delete();
	}

	// public static boolean depalcerIN(String nomFichierIN, String
	// nomFichierINSave)
	// {
	// Log.info("#################################################################");
	// Log.info("################ DEPLACEMENT DU FICHIER ["+nomFichierIN+"] A
	// L'EMPLACEMENT ["+nomFichierINSave+"] ");
	// FileChannel in = null;
	// FileChannel out = null;
	// boolean retour = false;
	// try
	// {
	// in = new FileInputStream(nomFichierIN).getChannel();
	// out = new FileOutputStream(nomFichierINSave).getChannel();
	//
	// in.transferTo(0L, in.size(), out);
	// retour = true;
	//
	// } catch (Exception e) {
	// retour = false;
	// DisplayExceptionDetail(e, "depalcerIN");
	// if (in != null)
	// try {
	// in.close();
	// } catch (IOException localIOException) {
	// DisplayExceptionDetail(e, "depalcerIN");
	// }
	// if (out != null)
	// try {
	// out.close();
	// }
	// catch (IOException localIOException1)
	// {
	// DisplayExceptionDetail(e, "depalcerIN");
	// }
	// }
	// finally
	// {
	// if (in != null)
	// try {
	// in.close();
	// } catch (IOException localIOException2)
	// {DisplayExceptionDetail(localIOException2, "depalcerIN");
	// }
	// if (out != null)
	// try {
	// out.close();
	// }
	// catch (IOException localIOException3)
	// {DisplayExceptionDetail(localIOException3, "depalcerIN");
	// }
	// }
	// Log.info("################ FICHIER ["+nomFichierIN+"] SUPPRIMER
	// "+supprimerFichier(nomFichierIN));
	// Log.info("################ FICHIER ["+nomFichierINSave+"] CREE ");
	// Log.info("#################################################################");
	//
	// return retour;
	// }
	public static boolean WriteFile(String FileName, String Contenu, boolean append) {

		FileWriter writer = null;
		boolean rep = false;

		try {
			writer = new FileWriter(FileName, append);
			writer.write(Contenu);
			rep = true;
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return rep;
	}

	public static void main(String[] args) {

		String refsdm = "00701596207";

		System.out.println(refsdm.substring(0, 10));

	}
}
