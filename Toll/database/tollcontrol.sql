-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 30-05-2015 a las 18:00:00
-- Versión del servidor: 5.5.43-0ubuntu0.14.04.1
-- Versión de PHP: 5.5.9-1ubuntu4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `tollcontrol`
--
CREATE DATABASE IF NOT EXISTS `tollcontrol` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `tollcontrol`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `connection`
--

CREATE TABLE IF NOT EXISTS `connection` (
  `tollid` int(11) NOT NULL,
  `intersection` int(11) NOT NULL,
  PRIMARY KEY (`tollid`,`intersection`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `infraction`
--

CREATE TABLE IF NOT EXISTS `infraction` (
  `plate` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tollid` int(11) NOT NULL,
  `booth` int(11) NOT NULL,
  PRIMARY KEY (`date`,`tollid`,`booth`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `intersection`
--

CREATE TABLE IF NOT EXISTS `intersection` (
  `intid` int(11) NOT NULL,
  `intname` varchar(30) NOT NULL,
  PRIMARY KEY (`intid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `manager`
--

CREATE TABLE IF NOT EXISTS `manager` (
  `managerid` int(11) NOT NULL,
  `tollid` int(11) NOT NULL,
  PRIMARY KEY (`managerid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `operator`
--

CREATE TABLE IF NOT EXISTS `operator` (
  `tollid` int(11) NOT NULL,
  `booth` int(11) NOT NULL,
  `operatorid` int(11) NOT NULL,
  PRIMARY KEY (`operatorid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `toll`
--

CREATE TABLE IF NOT EXISTS `toll` (
  `tollid` int(11) NOT NULL,
  `tollname` varchar(30) NOT NULL,
  PRIMARY KEY (`tollid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tollbooth`
--

CREATE TABLE IF NOT EXISTS `tollbooth` (
  `tollid` int(11) NOT NULL,
  `booth` int(11) NOT NULL,
  `boothtype` int(11) NOT NULL,
  PRIMARY KEY (`tollid`,`booth`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tollcash`
--

CREATE TABLE IF NOT EXISTS `tollcash` (
  `amount` double NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tollid` int(11) NOT NULL,
  `booth` int(11) NOT NULL,
  PRIMARY KEY (`date`,`tollid`,`booth`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tollphoto`
--

CREATE TABLE IF NOT EXISTS `tollphoto` (
  `amount` double unsigned NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tollid` int(10) unsigned NOT NULL,
  `plate` varchar(6) NOT NULL,
  `booth` int(10) unsigned NOT NULL,
  PRIMARY KEY (`date`,`tollid`,`booth`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tollsensor`
--

CREATE TABLE IF NOT EXISTS `tollsensor` (
  `amount` double unsigned NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tollid` int(10) unsigned NOT NULL,
  `sensorid` int(10) unsigned NOT NULL,
  `booth` int(10) unsigned NOT NULL,
  PRIMARY KEY (`date`,`tollid`,`booth`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `userphoto`
--

CREATE TABLE IF NOT EXISTS `userphoto` (
  `userid` int(11) NOT NULL,
  `upname` varchar(30) NOT NULL,
  `plate` varchar(6) NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usersensor`
--

CREATE TABLE IF NOT EXISTS `usersensor` (
  `userid` int(11) NOT NULL,
  `usname` varchar(30) NOT NULL,
  `sensorid` int(11) NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
