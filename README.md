# Deminator 💣

Un jeu de **Démineur multijoueur** en temps réel avec différents modes de jeu, conçu comme un monorepo TypeScript moderne.

## 🚀 Fonctionnalités

- **Multijoueur en temps réel** : Connectez-vous avec vos amis et jouez ensemble sur la même grille grâce aux WebSockets.
- **Plusieurs modes de jeu** :
  - **Classique (Coopératif / Compétitif)** : Révélez les cases ensemble, marquez des points pour chaque case sécurisée ou perdez-en en touchant une mine.
  - **Tour par Tour (Turn-by-Turn)** : Jouez de manière stratégique, chaque joueur joue l'un après l'autre.
  - **Simultané (Simultaneous)** : Action effrénée où tout le monde clique en temps réel.
- **Messagerie Intégrée (Chat)** : Communiquez et élaborez des stratégies directement depuis l'interface de jeu.
- **Tableau des scores (Leaderboard)** : Suivez les scores et statistiques des joueurs de la partie en cours.
- **Lobby Interactif** : Créez une partie, choisissez le mode de jeu, la taille de la grille, la densité des mines, puis lancez la partie !

---

## 🛠️ Stack Technique

### Monorepo
Le projet est organisé sous forme de **npm workspaces** :

- **`apps/client` (Frontend)** :
  - **React 18** avec **TypeScript**
  - **Vite** comme bundler et serveur de développement ultra-rapide
  - **Tailwind CSS** pour une interface moderne et responsive
  - **Lucide React** pour les icônes
  - **Socket.io-client** pour la communication en temps réel

- **`apps/server` (Backend)** :
  - **Node.js** et **TypeScript**
  - **Express**
  - **Socket.io** pour la gestion des connexions en temps réel
  - **tsx** pour exécuter directement le TypeScript en développement

---

## 📦 Installation et Lancement

### Prérequis
Assurez-vous d'avoir installé :
- [Node.js](https://nodejs.org/) (v18 ou supérieur recommandé)
- [npm](https://www.npmjs.com/)

### Étape 1 : Cloner le dépôt et installer les dépendances
À la racine du projet, lancez la commande suivante pour installer toutes les dépendances du client et du serveur :
```bash
npm run install:all
```

### Étape 2 : Lancer le projet en mode développement
Pour démarrer simultanément le frontend (sur `http://localhost:5173`) et le backend (sur `http://localhost:3000`), lancez :
```bash
npm run dev
```

### Étape 3 : Compiler pour la production
Pour générer les builds de production pour le client et le serveur :
```bash
npm run build
```

---

## 📂 Architecture des Dossiers

```text
deminator/
├── apps/
│   ├── client/                 # Application Frontend React
│   │   ├── src/
│   │   │   ├── components/     # Composants React (Board, Chat, Lobby, Header, Leaderboard...)
│   │   │   ├── modes/          # Logique spécifique aux modes de jeu côté client
│   │   │   ├── types/          # Types partagés ou spécifiques au client
│   │   │   ├── App.tsx
│   │   │   └── main.tsx
│   │   └── package.json
│   │
│   └── server/                 # Serveur Backend Node.js
│       ├── src/
│       │   ├── controllers/    # Gestionnaire de la logique de jeu (GameController)
│       │   ├── models/         # Modèles de données (GameRoom)
│       │   ├── modes/          # Handlers de modes de jeu (Classic, TurnByTurn, Simultaneous)
│       │   ├── types/          # Définitions des types (Player, ServerCell, ClientCell, GameConfig...)
│       │   ├── views/          # Formatage des données envoyées aux clients (GameView)
│       │   └── index.ts        # Point d'entrée du serveur
│       └── package.json
│
├── package.json                # Configuration globale du monorepo
└── README.md                   # Ce fichier de documentation
```

---

## 🎮 Règles de Jeu & Modes

1. **Création d'un Salon (Lobby)** :
   - Rejoignez le serveur avec votre pseudo.
   - Configurez les dimensions de la grille (lignes, colonnes) et le nombre de mines.
   - Choisissez un mode de jeu.

2. **Les Modes de Jeu** :
   - **Mode Classique** : Le démineur traditionnel, réinventé en coopératif/compétitif direct.
   - **Mode Tour par Tour** : Un indicateur visuel montre qui doit jouer. Vous devez attendre votre tour pour effectuer un clic.
   - **Mode Simultané** : Pas d'attente, tout le monde clique à volonté. La rapidité et la précision sont les clés !
