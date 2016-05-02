package util; /**
 *
 *	La classe util.JPImage est un panel ayant une (ou plusieurs) image(s) peinte(s)
 *		en fond.
 *
 *	Permet:
 *		Empiler des images transparentes(ou non) sur d'autres images
 *		Modifier dynamiquement les images a afficher sur le panel
 *		Gerer l'ordre des images (lesquelles seront peintes par dessus lesquelles)
 *		Eviter un débordement de mémoire d'image (si plusieurs fois la méme image chargée)
 *		Redimentionner les images en fonction des tailles du panel
 *				(avec un rendu correct malgré temps de chargement important (Ordre de la seconde pour un premier chargement d'image))
 *
 *	Ne permet pas:
 *		Forcer une dimension ou une proportion a l'image déssinée dans le panel
 *			(Il faudra pour cela parametrer le layout du contenant du util.JPImage)
 *
 *
 *
 * Version 1.0.0: ("PanelImage v1.0.0" Auteur: Manuel Pavone)
 *		+ Version Initiale
 *
 * Version 1.0.1: (Auteur des modifications: Nathaël Noguès)
 * 		+ Modifications pour permettre la transparence des images
 *
 * Version 2.0.0:
 * 		+	Plusieurs images possibles sur une meme util.JPImage (permet la superposition d'images transparentes)
 * 			+ Ajout de méthodes addImage, removeImage
 * 			+ Modifications attribu cheminImage => cheminsImages et modifications associées
 * 		+ Ajout d'un attribu static "images" permettant de ne pas avoir a recharger une meme image plusieurs fois
 *
 *	# VERSION FINALE #
 *
 * @author Nathaël Noguès
 * @version 2.0.0
*/

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class JPImage extends JPanel {
	private static final long serialVersionUID = -6630783547440050700L;
	/**
	 * Contiens les images redimentionnées (objet Image), liés par leur taille (clés Dimension), ainsi que par l'adresse de l'image originale (clé String)
	 * Permet de ne pas a avoir à redimentionner plusieurs fois une méme image inutilement (si l'image de dimension voulue est déjà en mémoire)
	 */
	private static final HashMap<String, HashMap<Dimension, Image>> images = new HashMap<String, HashMap<Dimension, Image>>();
	/**
	 * Contiens les images chargés en mémoire (en objet), liés par leur adresses (en clé)
	 * Permet de ne pas a avoir à effectuer plusieurs acces systémes inutiles (si l'image est déjà chargée en mémoire)
	 */
	private static final HashMap<String, Image> imagesOriginales = new HashMap<String, Image>();

	/**
	 * Images à afficher (elles seront affichés les unes empilées sur les autres, l'indice 0 étant l'image "par dessous toutes les autres", l'indice n étant l'image "par dessus toutes les autres"
	 */
	private ArrayList<String> cheminsImages;


	//	//	//	//	//	CONSTRUCTEURS	//	//	//	//	//
	/**
	 * Constructeur par défaut (le util.JPImage est alors à sa création, similaire à un simple JPanel)
	 */
	public JPImage()
	{	cheminsImages = new ArrayList<String>();	}

	/**
	 * Constructeur de util.JPImage
	 * @param adrs Adresses des images (séparés par des virgules, la premiére étant celle de fond, la derniére celle par dessus toutes)
	 */
	public JPImage(final String ... adrs)
	{	cheminsImages = new ArrayList<String>();
		//	Ajouter les images en paramétre une a une
		for(String s : adrs)
			cheminsImages.add(s);
	}

	//	//	//	//	//	METHODES PRIVEES	//	//	//	//	//
	/**
	 * 	Cette méthode cherchera parmi les images déjà chargées si la demande y est déjà présente.
	 * 	Dans le cas contraire, la méthode se chargera de redimentionner l'image, voir de la charger en mémoire
	 * 	si cela n'as pas non plus été le cas.
	 *
	 * @param cheminImage Chemin de l'image cherché
	 * @param dim Dimensions voulues pour l'image cherchée
	 * @return Image cherchée (null si introuvable et non chargeable)
	 */
	private Image trouverImageDimension(final String cheminImage, final Dimension dim)
	{	try{
			//	Initialier le paramétre de retour
			Image image = null;

			//	Vérifier si l'image cherchée à déjà été chargée en mémoire aux dimensions souhaités
			if(images.containsKey(cheminImage))
				image = images.get(cheminImage).get(this.getSize());
			//	Sinon, préparer la mémoire des images chargés à son intégration
			else
				images.put(cheminImage, new HashMap<Dimension, Image>());

			if(image != null)	// Si l'image à été trouvée, la retourner
				return image;

			// Si l'image n'as pas été trouvée aux dimensions souhaités:
			//	regarder si l'originale à déjà été chargée
			image = imagesOriginales.get(cheminImage);
			if(image == null)
			{	// sinon charger l'image originale
				image = ImageIO.read(new File(cheminImage));

				// Puis ajouter l'image chargée en mémoire (pour ne plus a avoir à la recharger plus tard)
				imagesOriginales.put(cheminImage, image);
			}

			//	Redimensionner l'image (utilise un algorithme lent mais visuellement performant)
			Image resized = image.getScaledInstance((int)dim.getWidth(), (int)dim.getHeight(), Image.SCALE_AREA_AVERAGING);

			//	Ajouter l'image aux images déjà chargés
			HashMap<Dimension, Image> hmDI = images.get(cheminImage);
			hmDI.put(dim, resized);

			// Controler le chargement de l'image redimensionnée
			System.gc();	//	Vider les mémoires inutilisés pendant l'attente du média Tracker
			MediaTracker mt = new MediaTracker(this);
			mt.addImage(resized, 1);
			mt.waitForAll();

			//	Retourner l'image toute juste crée (ou juste redimensionnée)
			return resized;
		} catch (Exception e) { e.printStackTrace(); }

		//	S'il y a eu une erreur, retourner null (image non trouvée et impossibilité de la charger)
		System.err.println("Image introuvable: "+cheminImage);
		return null;
	}

	@Override
	protected void paintComponent(final Graphics g)
	{	super.paintComponent(g);

		//	Pour chacune des images empilées
		for(String cheminImage : cheminsImages)
		{	// Chercher l'image aux dimensions souhaités
			Image resized = trouverImageDimension(cheminImage, this.getSize());

			// Tracer l'image par dessus
			g.drawImage(resized, 0, 0, getWidth(), getHeight(), this);
		}
	}

	//	//	//	//	//	METHODES PUBLIQUES	//	//	//	//	//
	/**
	 * @param chemin Chemin de l'image qui sera déssinée par dessus le panel (et les autres images déjà ajoutés)
	 */
	public void addImage(final String chemin)
	{	//	contournement d'une erreur probable
		if(chemin == null)
			return;
		cheminsImages.add(chemin);
	}

	/**
	 * @param chemin Chemin de l'image à ne plus dessiner sur le panel
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
	 * Efface les mémoires des images chargés, afin de forcer le rechargement de toutes les images
	 */
	public static void refreshAll()
	{	images.clear();
		imagesOriginales.clear();
	}

	/**
	 * @return Liste contenant les chemins de toutes les images empilées sur le util.JPImage courant
	 */
	public ArrayList<String> getChemins()
	{	return cheminsImages;	}

	/**
	 * @return Nombre d'image actuellement chargés en mémoire
	 */
	public static int nbImages()
	{	int i = 0;
		// Pour chaque image, compter le nombre de redimentions existantes
		for(String s : images.keySet())
			i+=images.get(s).size();

		return i;
	}

	/**
	 * @param keySet Toutes les images dont les chemins auront été passés en paramétres seront chargés dans l'application
	 * 			Permet d'effectuer le chargement des images (calcul des redimensions non compris) affin d'accélérer le
	 * 			chargement et l'utilisation future de celles-ci
	 */
	public static void preloadImages(Iterable<String> keySet)
	{	//	Pour chaque chemin en paramétre
		for(String cheminImage : keySet)
			try{
				// Verifier si l'image n'é jamais été chargée en mémoire
				if(!imagesOriginales.containsKey(cheminImage))
				{	//	Alors charger l'image et la placer en mémoire
					Image image = ImageIO.read(new File(cheminImage));
					imagesOriginales.put(cheminImage, image);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
	}

}