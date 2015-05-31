-- phpMyAdmin SQL Dump
-- version 4.4.8
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 31, 2015 at 05:04 AM
-- Server version: 5.6.25
-- PHP Version: 5.5.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `tollcontrol`
--

-- --------------------------------------------------------

--
-- Table structure for table `connection`
--

CREATE TABLE IF NOT EXISTS `connection` (
  `tollid` int(11) NOT NULL,
  `intersection` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `connection`
--

INSERT INTO `connection` (`tollid`, `intersection`) VALUES
(1, 1),
(2, 2),
(3, 2),
(4, 3),
(5, 3),
(6, 4),
(7, 5),
(8, 6),
(9, 6),
(10, 7),
(11, 7),
(12, 8),
(1, 9),
(2, 9),
(7, 9),
(8, 9),
(13, 9),
(3, 10),
(4, 10),
(9, 10),
(10, 10),
(13, 10),
(14, 10),
(5, 11),
(6, 11),
(11, 11),
(12, 11),
(14, 11);

-- --------------------------------------------------------

--
-- Table structure for table `infraction`
--

CREATE TABLE IF NOT EXISTS `infraction` (
  `plate` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tollid` int(11) NOT NULL,
  `booth` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `intersection`
--

CREATE TABLE IF NOT EXISTS `intersection` (
  `intid` int(11) NOT NULL,
  `intname` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `intersection`
--

INSERT INTO `intersection` (`intid`, `intname`) VALUES
(1, 'Entrada1'),
(2, 'Entrada2'),
(3, 'Entrada3'),
(4, 'Entrada4'),
(5, 'Entrada5'),
(6, 'Entrada6'),
(7, 'Entrada7'),
(8, 'Entrada8'),
(9, 'Int1'),
(10, 'Int2'),
(11, 'Int3');

-- --------------------------------------------------------

--
-- Table structure for table `manager`
--

CREATE TABLE IF NOT EXISTS `manager` (
  `managerid` int(11) NOT NULL,
  `tollid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `manager`
--

INSERT INTO `manager` (`managerid`, `tollid`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(11, 11),
(12, 12),
(13, 13),
(14, 14);

-- --------------------------------------------------------

--
-- Table structure for table `operator`
--

CREATE TABLE IF NOT EXISTS `operator` (
  `tollid` int(11) NOT NULL,
  `booth` int(11) NOT NULL,
  `operatorid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `operator`
--

INSERT INTO `operator` (`tollid`, `booth`, `operatorid`) VALUES
(1, 1, 1),
(1, 2, 2),
(1, 3, 3),
(1, 4, 4),
(1, 5, 5),
(1, 6, 6),
(2, 1, 7),
(2, 2, 8),
(2, 3, 9),
(2, 4, 10),
(2, 5, 11),
(2, 6, 12),
(3, 1, 13),
(3, 2, 14),
(3, 3, 15),
(3, 4, 16),
(3, 5, 17),
(3, 6, 18),
(4, 1, 19),
(4, 2, 20),
(4, 3, 21),
(4, 4, 22),
(4, 5, 23),
(4, 6, 24),
(5, 1, 25),
(5, 2, 26),
(5, 3, 27),
(5, 4, 28),
(5, 5, 29),
(5, 6, 30),
(6, 1, 31),
(6, 2, 32),
(6, 3, 33),
(6, 4, 34),
(6, 5, 35),
(6, 6, 36),
(7, 1, 37),
(7, 2, 38),
(7, 3, 39),
(7, 4, 40),
(7, 5, 41),
(7, 6, 42),
(8, 1, 43),
(8, 2, 44),
(8, 3, 45),
(8, 4, 46),
(8, 5, 47),
(8, 6, 48),
(9, 1, 49),
(9, 2, 50),
(9, 3, 51),
(9, 4, 52),
(9, 5, 53),
(9, 6, 54),
(10, 1, 55),
(10, 2, 56),
(10, 3, 57),
(10, 4, 58),
(10, 5, 59),
(10, 6, 60),
(11, 1, 61),
(11, 2, 62),
(11, 3, 63),
(11, 4, 64),
(11, 5, 65),
(11, 6, 66),
(12, 1, 67),
(12, 2, 68),
(12, 3, 69),
(12, 4, 70),
(12, 5, 71),
(12, 6, 72),
(13, 1, 73),
(13, 2, 74),
(13, 3, 75),
(13, 4, 76),
(13, 5, 77),
(13, 6, 78),
(14, 1, 79),
(14, 2, 80),
(14, 3, 81),
(14, 4, 82),
(14, 5, 83),
(14, 6, 84);

-- --------------------------------------------------------

--
-- Table structure for table `toll`
--

CREATE TABLE IF NOT EXISTS `toll` (
  `tollid` int(11) NOT NULL,
  `tollname` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `toll`
--

INSERT INTO `toll` (`tollid`, `tollname`) VALUES
(1, 'toll1-9'),
(2, 'toll2-9'),
(3, 'toll2-10'),
(4, 'toll3-10'),
(5, 'toll3-11'),
(6, 'toll4-11'),
(7, 'toll5-9'),
(8, 'toll6-9'),
(9, 'toll6-10'),
(10, 'toll7-10'),
(11, 'toll7-11'),
(12, 'toll8-11'),
(13, 'toll9-10'),
(14, 'toll10-11');

-- --------------------------------------------------------

--
-- Table structure for table `tollbooth`
--

CREATE TABLE IF NOT EXISTS `tollbooth` (
  `tollid` int(11) NOT NULL,
  `booth` int(11) NOT NULL,
  `boothtype` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tollbooth`
--

INSERT INTO `tollbooth` (`tollid`, `booth`, `boothtype`) VALUES
(1, 1, 1),
(1, 2, 2),
(1, 3, 3),
(1, 4, 1),
(1, 5, 2),
(1, 6, 3),
(2, 1, 1),
(2, 2, 2),
(2, 3, 3),
(2, 4, 1),
(2, 5, 2),
(2, 6, 3),
(3, 1, 1),
(3, 2, 2),
(3, 3, 3),
(3, 4, 1),
(3, 5, 2),
(3, 6, 3),
(4, 1, 1),
(4, 2, 2),
(4, 3, 3),
(4, 4, 1),
(4, 5, 2),
(4, 6, 3),
(5, 1, 1),
(5, 2, 2),
(5, 3, 3),
(5, 4, 1),
(5, 5, 2),
(5, 6, 3),
(6, 1, 1),
(6, 2, 2),
(6, 3, 3),
(6, 4, 1),
(6, 5, 2),
(6, 6, 3),
(7, 1, 1),
(7, 2, 2),
(7, 3, 3),
(7, 4, 1),
(7, 5, 2),
(7, 6, 3),
(8, 1, 1),
(8, 2, 2),
(8, 3, 3),
(8, 4, 1),
(8, 5, 2),
(8, 6, 3),
(9, 1, 1),
(9, 2, 2),
(9, 3, 3),
(9, 4, 1),
(9, 5, 2),
(9, 6, 3),
(10, 1, 1),
(10, 2, 2),
(10, 3, 3),
(10, 4, 1),
(10, 5, 2),
(10, 6, 3),
(11, 1, 1),
(11, 2, 2),
(11, 3, 3),
(11, 4, 1),
(11, 5, 2),
(11, 6, 3),
(12, 1, 1),
(12, 2, 2),
(12, 3, 3),
(12, 4, 1),
(12, 5, 2),
(12, 6, 3),
(13, 1, 1),
(13, 2, 2),
(13, 3, 3),
(13, 4, 1),
(13, 5, 2),
(13, 6, 3),
(14, 1, 1),
(14, 2, 2),
(14, 3, 3),
(14, 4, 1),
(14, 5, 2),
(14, 6, 3);

-- --------------------------------------------------------

--
-- Table structure for table `tollcash`
--

CREATE TABLE IF NOT EXISTS `tollcash` (
  `amount` double NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tollid` int(11) NOT NULL,
  `booth` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tollphoto`
--

CREATE TABLE IF NOT EXISTS `tollphoto` (
  `amount` double NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tollid` int(10) NOT NULL,
  `plate` varchar(6) NOT NULL,
  `booth` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tollsensor`
--

CREATE TABLE IF NOT EXISTS `tollsensor` (
  `amount` double NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tollid` int(10) NOT NULL,
  `sensorid` int(10) NOT NULL,
  `booth` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `userid` int(11) NOT NULL,
  `uname` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `sensorid` int(11) DEFAULT NULL,
  `plate` varchar(6) DEFAULT NULL,
  `funds` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `connection`
--
ALTER TABLE `connection`
  ADD PRIMARY KEY (`tollid`,`intersection`),
  ADD KEY `intersection` (`intersection`);

--
-- Indexes for table `infraction`
--
ALTER TABLE `infraction`
  ADD PRIMARY KEY (`date`,`tollid`,`booth`),
  ADD KEY `tollid` (`tollid`,`booth`);

--
-- Indexes for table `intersection`
--
ALTER TABLE `intersection`
  ADD PRIMARY KEY (`intid`);

--
-- Indexes for table `manager`
--
ALTER TABLE `manager`
  ADD PRIMARY KEY (`managerid`),
  ADD UNIQUE KEY `managerid` (`managerid`),
  ADD UNIQUE KEY `tollid` (`tollid`);

--
-- Indexes for table `operator`
--
ALTER TABLE `operator`
  ADD PRIMARY KEY (`operatorid`),
  ADD KEY `tollid` (`tollid`),
  ADD KEY `booth` (`booth`),
  ADD KEY `tollid_2` (`tollid`,`booth`);

--
-- Indexes for table `toll`
--
ALTER TABLE `toll`
  ADD PRIMARY KEY (`tollid`);

--
-- Indexes for table `tollbooth`
--
ALTER TABLE `tollbooth`
  ADD PRIMARY KEY (`tollid`,`booth`);

--
-- Indexes for table `tollcash`
--
ALTER TABLE `tollcash`
  ADD PRIMARY KEY (`date`,`tollid`,`booth`),
  ADD KEY `tollid` (`tollid`,`booth`);

--
-- Indexes for table `tollphoto`
--
ALTER TABLE `tollphoto`
  ADD PRIMARY KEY (`date`,`tollid`,`booth`),
  ADD KEY `tollid` (`tollid`,`booth`),
  ADD KEY `plate` (`plate`);

--
-- Indexes for table `tollsensor`
--
ALTER TABLE `tollsensor`
  ADD PRIMARY KEY (`date`,`tollid`,`booth`),
  ADD KEY `tollid` (`tollid`,`booth`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userid`),
  ADD UNIQUE KEY `userid` (`userid`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `connection`
--
ALTER TABLE `connection`
  ADD CONSTRAINT `connection_ibfk_1` FOREIGN KEY (`tollid`) REFERENCES `toll` (`tollid`),
  ADD CONSTRAINT `connection_ibfk_2` FOREIGN KEY (`intersection`) REFERENCES `intersection` (`intid`);

--
-- Constraints for table `infraction`
--
ALTER TABLE `infraction`
  ADD CONSTRAINT `infraction_ibfk_1` FOREIGN KEY (`tollid`, `booth`) REFERENCES `tollbooth` (`tollid`, `booth`),
  ADD CONSTRAINT `infraction_ibfk_2` FOREIGN KEY (`tollid`) REFERENCES `toll` (`tollid`);

--
-- Constraints for table `manager`
--
ALTER TABLE `manager`
  ADD CONSTRAINT `manager_ibfk_1` FOREIGN KEY (`tollid`) REFERENCES `toll` (`tollid`);

--
-- Constraints for table `operator`
--
ALTER TABLE `operator`
  ADD CONSTRAINT `operator_ibfk_1` FOREIGN KEY (`tollid`, `booth`) REFERENCES `tollbooth` (`tollid`, `booth`),
  ADD CONSTRAINT `operator_ibfk_2` FOREIGN KEY (`tollid`) REFERENCES `toll` (`tollid`);

--
-- Constraints for table `tollbooth`
--
ALTER TABLE `tollbooth`
  ADD CONSTRAINT `tollbooth_ibfk_1` FOREIGN KEY (`tollid`) REFERENCES `toll` (`tollid`);

--
-- Constraints for table `tollcash`
--
ALTER TABLE `tollcash`
  ADD CONSTRAINT `tollcash_ibfk_1` FOREIGN KEY (`tollid`, `booth`) REFERENCES `tollbooth` (`tollid`, `booth`),
  ADD CONSTRAINT `tollcash_ibfk_2` FOREIGN KEY (`tollid`) REFERENCES `toll` (`tollid`);

--
-- Constraints for table `tollphoto`
--
ALTER TABLE `tollphoto`
  ADD CONSTRAINT `tollphoto_ibfk_1` FOREIGN KEY (`tollid`, `booth`) REFERENCES `tollbooth` (`tollid`, `booth`),
  ADD CONSTRAINT `tollphoto_ibfk_2` FOREIGN KEY (`tollid`) REFERENCES `toll` (`tollid`);

--
-- Constraints for table `tollsensor`
--
ALTER TABLE `tollsensor`
  ADD CONSTRAINT `tollsensor_ibfk_1` FOREIGN KEY (`tollid`, `booth`) REFERENCES `tollbooth` (`tollid`, `booth`),
  ADD CONSTRAINT `tollsensor_ibfk_2` FOREIGN KEY (`tollid`) REFERENCES `toll` (`tollid`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
