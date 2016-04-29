package client; /**
 *
 *	La classe client.JPImage est un panel ayant une (ou plusieurs) image(s) peinte(s)
 *		en fond.
 *
 *	Permet:
 *		Empiler des images transparentes(ou non) sur d'autres images
 *		Modifier dynamiquement les images a afficher sur le panel
 *		Gerer l'ordre des images (lesquelles seront peintes par dessus lesquelles)
 *		Eviter un d�bordement de m�moire d'image (si plusieurs fois la m�me image charg�e)
 *		Redimentionner les images en fonction des tailles du panel
 *				(avec un rendu correct malgr� temps de chargement important (Ordre de la seconde pour un premier chargement d'image))
 *
 *	Ne permet pas:
 *		Forcer une dimension ou une proportion a l'image d�ssin�e dans le panel
 *			(Il faudra pour cela parametrer le layout du contenant du client.JPImage)
 *
 *
 *
 * Version 1.0.0: ("PanelImage v1.0.0" Auteur: Manuel Pavone)
 *		+ Version Initiale
 *
 * Version 1.0.1: (Auteur des modifications: Natha�l Nogu�s)
 * 		+ Modifications pour permettre la transparence des images
 *
 * Version 2.0.0:
 * 		+	Plusieurs images possibles sur une meme client.JPImage (permet la superposition d'images transparentes)
 * 			+ Ajout de m�thodes addImage, removeImage
 * 			+ Modifications attribu cheminImage => cheminsImages et modifications associ�es
 * 		+ Ajout d'un attribu static "images" permettant de ne pas avoir a recharger une meme image plusieurs fois
 *
 *	# VERSION FINALE #
 *
 * @author Natha�l Nogu�s
 * @version 2.0.0
*/

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JPImage extends JPanel {
	private static final long serialVersionUID = -6630783547440050700L;
	/**
	 * Contiens les images redimentionn�es (objet Image), li�s par leur taille (cl�s Dimension), ainsi que par l'adresse de l'image originale (cl� String)
	 * Permet de ne pas a avoir � redimentionner plusieurs fois une m�me image inutilement (si l'image de dimension voulue est d�j� en m�moire)
	 */
	private static final HashMap<String, HashMap<Dimension, Image>> images = new HashMap<String, HashMap<Dimension, Image>>();
	/**
	 * Contiens les images charg�s en m�moire (en objet), li�s par leur adresses (en cl�)
	 * Permet de ne pas a avoir � effectuer plusieurs acces syst�mes inutiles (si l'image est d�j� charg�e en m�moire)
	 */
	private static final HashMap<String, Image> imagesOriginales = new HashMap<String, Image>();

	/**
	 * Images � afficher (elles seront affich�s les unes empil�es sur les autres, l'indice 0 �tant l'image "par dessous toutes les autres", l'indice n �tant l'image "par dessus toutes les autres"
	 */
	private ArrayList<String> cheminsImages;


	//	//	//	//	//	CONSTRUCTEURS	//	//	//	//	//
	/**
	 * Constructeur par d�faut (le client.JPImage est alors � sa cr�ation, similaire � un simple JPanel)
	 */
	public JPImage()
	{	cheminsImages = new ArrayList<String>();	}

	/**
	 * Constructeur de client.JPImage
	 * @param adrs Adresses des images (s�par�s par des virgules, la premi�re �tant celle de fond, la derni�re celle par dessus toutes)
	 */
	public JPImage(final String ... adrs)
	{	cheminsImages = new ArrayList<String>();
		//	Ajouter les images en param�tre une a une
		for(String s : adrs)
			cheminsImages.add(s);
	}

	//	//	//	//	//	METHODES PRIVEES	//	//	//	//	//
	/**
	 * 	Cette m�thode cherchera parmi les images d�j� charg�es si la demande y est d�j� pr�sente.
	 * 	Dans le cas contraire, la m�thode se chargera de redimentionner l'image, voir de la charger en m�moire
	 * 	si cela n'as pas non plus �t� le cas.
	 *
	 * @param cheminImage Chemin de l'image cherch�
	 * @param dim Dimensions voulues pour l'image cherch�e
	 * @return Image cherch�e (null si introuvable et non chargeable)
	 */
	private Image trouverImageDimension(final String cheminImage, final Dimension dim)
	{	try{
			//	Initialier le param�tre de retour
			Image image = null;

			//	V�rifier si l'image cherch�e � d�j� �t� charg�e en m�moire aux dimensions souhait�s
			if(images.containsKey(cheminImage))
				image = images.get(cheminImage).get(this.getSize());
			//	Sinon, pr�parer la m�moire des images charg�s � son int�gration
			else
				images.put(cheminImage, new HashMap<Dimension, Image>());

			if(image != null)	// Si l'image � �t� trouv�e, la retourner
				return image;

			// Si l'image n'as pas �t� trouv�e aux dimensions souhait�s:
			//	regarder si l'originale � d�j� �t� charg�e
			image = imagesOriginales.get(cheminImage);
			if(image == null)
			{	// sinon charger l'image originale
				image = ImageIO.read(new File(cheminImage));

				// Puis ajouter l'image charg�e en m�moire (pour ne plus a avoir � la recharger plus tard)
				imagesOriginales.put(cheminImage, image);
			}

			//	Redimensionner l'image (utilise un algorithme lent mais visuellement performant)
			Image resized = image.getScaledInstance((int)dim.getWidth(), (int)dim.getHeight(), Image.SCALE_AREA_AVERAGING);

			//	Ajouter l'image aux images d�j� charg�s
			HashMap<Dimension, Image> hmDI = images.get(cheminImage);
			hmDI.put(dim, resized);

			// Controler le chargement de l'image redimensionn�e
			System.gc();	//	Vider les m�moires inutilis�s pendant l'attente du m�dia Tracker
			MediaTracker mt = new MediaTracker(this);
			mt.addImage(resized, 1);
			mt.waitForAll();

			//	Retourner l'image toute juste cr�e (ou juste redimensionn�e)
			return resized;
		} catch (Exception e) { e.printStackTrace(); }

		//	S'il y a eu une erreur, retourner null (image non trouv�e et impossibilit� de la charger)
		System.err.println("Image introuvable: "+cheminImage);
		return null;
	}

	@Override
	protected void paintComponent(final Graphics g)
	{	super.paintComponent(g);

		//	Pour chacune des images empil�es
		for(String cheminImage : cheminsImages)
		{	// Chercher l'image aux dimensions souhait�s
			Image resized = trouverImageDimension(cheminImage, this.getSize());

			// Tracer l'image par dessus
			g.drawImage(resized, 0, 0, getWidth(), getHeight(), this);
		}
	}

	//	//	//	//	//	METHODES PUBLIQUES	//	//	//	//	//
	/**
	 * @param chemin Chemin de l'image qui sera d�ssin�e par dessus le panel (et les autres images d�j� ajout�s)
	 */
	public void addImage(final String chemin)
	{	//	contournement d'une erreur probable
		if(chemin == null)
			return;
		cheminsImages.add(chemin);
	}

	/**
	 * @param chemin Chemin de l'image � ne plus dessiner sur le panel
	 */
	public void rmImage(final String chemin)
	{	// Contournement d'une erreur possible
		if(chemin == null)
			return;
		cheminsImages.remove(chemin);
	}

	/**
	 * Effacer toutes les images du panel
	 */
	public void rmImages()
	{	cheminsImages.clear();	}

	/**
	 * Efface les m�moires des images charg�s, afin de forcer le rechargement de toutes les images
	 */
	public static void refreshAll()
	{	images.clear();
		imagesOriginales.clear();
	}

	/**
	 * @return Liste contenant les chemins de toutes les images empil�es sur le client.JPImage courant
	 */
	public ArrayList<String> getChemins()
	{	return cheminsImages;	}

	/**
	 * @return Nombre d'image actuellement charg�s en m�moire
	 */
	public static int nbImages()
	{	int i = 0;
		// Pour chaque image, compter le nombre de redimentions existantes
		for(String s : images.keySet())
			i+=images.get(s).size();

		return i;
	}

	/**
	 * @param keySet Toutes les images dont les chemins auront �t� pass�s en param�tres seront charg�s dans l'application
	 * 			Permet d'effectuer le chargement des images (calcul des redimensions non compris) affin d'acc�l�rer le
	 * 			chargement et l'utilisation future de celles-ci
	 */
	public static void preloadImages(Iterable<String> keySet)
	{	//	Pour chaque chemin en param�tre
		for(String cheminImage : keySet)
			try{
				// Verifier si l'image n'� jamais �t� charg�e en m�moire
				if(!imagesOriginales.containsKey(cheminImage))
				{	//	Alors charger l'image et la placer en m�moire
					Image image = ImageIO.read(new File(cheminImage));
					imagesOriginales.put(cheminImage, image);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
	}

}