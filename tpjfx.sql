-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : ven. 21 avr. 2023 à 10:02
-- Version du serveur : 8.0.31
-- Version de PHP : 8.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `tpjfx`
--

-- --------------------------------------------------------

--
-- Structure de la table `auth`
--

DROP TABLE IF EXISTS `auth`;
CREATE TABLE IF NOT EXISTS `auth` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(1000) NOT NULL,
  `password` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `auth`
--

INSERT INTO `auth` (`id`, `login`, `password`) VALUES
(1, 'yvan', 'a548817d31ca8feba6324e4eb3eec6537d12d578f7ee08dd330edb8e8094bcaa');

-- --------------------------------------------------------

--
-- Structure de la table `clefs`
--

DROP TABLE IF EXISTS `clefs`;
CREATE TABLE IF NOT EXISTS `clefs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numero` int NOT NULL,
  `couleur` varchar(1000) NOT NULL,
  `description` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `clefs`
--

INSERT INTO `clefs` (`id`, `numero`, `couleur`, `description`) VALUES
(1, 108436, 'Rouge', 'Porte salon');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
