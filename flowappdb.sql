-- phpMyAdmin SQL Dump
-- version 5.0.4deb2+deb11u1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 19, 2023 at 04:00 AM
-- Server version: 10.5.19-MariaDB-0+deb11u2
-- PHP Version: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `flowappdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `Customer`
--

CREATE TABLE `Customer` (
  `email` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Customer`
--

INSERT INTO `Customer` (`email`, `telephone`, `user_id`) VALUES
('rona.lubowitz@yahoo.com', '760.417.4889', '039-64-6787'),
('albert.champlin@gmail.com', '(119) 862-6887', '049-37-2697'),
('jonathon.fadel@yahoo.com', '673.778.7859', '250-06-1734'),
('luis.schuster@hotmail.com', '(456) 441-3424', '476-78-6726'),
('trevor.carroll@hotmail.com', '1-775-182-4717', '600-31-2067'),
('jamel.mraz@yahoo.com', '1-071-589-1342', '609-08-2353'),
('yuki.ratke@yahoo.com', '(529) 337-0099', '697-42-8583'),
('esther.krajcik@hotmail.com', '1-667-987-9185', '747-87-0686'),
('phebe.brown@gmail.com', '1-689-463-3261', '790-02-1845'),
('huong.stehr@yahoo.com', '1-451-310-5668', '852-31-6648');

-- --------------------------------------------------------

--
-- Table structure for table `Employee`
--

CREATE TABLE `Employee` (
  `available_for_chat` bit(1) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Employee`
--

INSERT INTO `Employee` (`available_for_chat`, `role`, `user_id`) VALUES
(b'0', 'Representative', '122-61-2739'),
(b'0', 'Representative', '167-46-3413'),
(b'0', 'Representative', '569-86-4772'),
(b'0', 'Representative', '573-22-3908'),
(b'0', 'Technician', '587-36-1728'),
(b'0', 'Technician', '600-24-6275'),
(b'1', 'Technician', '652-53-3519'),
(b'0', 'Technician', '787-65-4614'),
(b'0', 'Representative', '841-91-0332'),
(b'0', 'Representative', '844-80-7690');

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequences`
--

CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) NOT NULL,
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hibernate_sequences`
--

INSERT INTO `hibernate_sequences` (`sequence_name`, `next_val`) VALUES
('default', 51);

-- --------------------------------------------------------

--
-- Table structure for table `Message`
--

CREATE TABLE `Message` (
  `message_id` int(11) NOT NULL,
  `category` varchar(100) NOT NULL,
  `date_created` datetime NOT NULL,
  `details` varchar(2000) NOT NULL,
  `type` varchar(50) NOT NULL,
  `resolved` bit(1) DEFAULT NULL,
  `customer_id` varchar(255) DEFAULT NULL,
  `employee_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Message`
--

INSERT INTO `Message` (`message_id`, `category`, `date_created`, `details`, `type`, `resolved`, `customer_id`, `employee_id`) VALUES
(1, 'Internship Opportunities', '2023-07-18 21:47:57', 'That it should come to this!.', 'Make a Query', b'0', '250-06-1734', '841-91-0332'),
(2, 'Service Delivery', '2023-07-18 21:47:59', 'How bitter a thing it is to look into happiness through another man\'s eyes!', 'Request a Service', b'0', '697-42-8583', '844-80-7690'),
(3, 'Discrimination', '2023-07-18 21:48:01', 'And it must follow, as the night the day, thou canst not then be false to any man.', 'Lodge a Complaint', b'0', '697-42-8583', '844-80-7690'),
(4, 'Immigration Requirements', '2023-07-18 21:48:02', 'Off with his head!', 'Make a Query', b'0', '790-02-1845', '167-46-3413'),
(5, 'Discrimination', '2023-07-18 21:48:04', 'Though this be madness, yet there is method in \'t.', 'Lodge a Complaint', b'0', '790-02-1845', '122-61-2739'),
(6, 'Discrimination', '2023-07-18 21:48:05', 'Doubt that the sun doth move, doubt truth to be a liar, but never doubt I love.', 'Lodge a Complaint', b'0', '790-02-1845', '573-22-3908'),
(7, 'Campus Security', '2023-07-18 21:48:07', 'Off with his head!', 'Request a Service', b'0', '476-78-6726', '573-22-3908'),
(8, 'Disciplinary Matters', '2023-07-18 21:48:08', 'How bitter a thing it is to look into happiness through another man\'s eyes!', 'Lodge a Complaint', b'0', '049-37-2697', '787-65-4614'),
(9, 'Immigration Requirements', '2023-07-18 21:48:10', 'To be, or not to be: that is the question.', 'Make a Query', b'0', '852-31-6648', '600-24-6275'),
(10, 'Cultural Adjustment', '2023-07-18 21:48:12', 'This above all: to thine own self be true.', 'Make a Query', b'0', '250-06-1734', '573-22-3908'),
(11, 'Internship Opportunities', '2023-07-18 21:48:13', 'Can one desire too much of a good thing?.', 'Make a Query', b'0', '476-78-6726', '787-65-4614'),
(12, 'Cultural Adjustment', '2023-07-18 21:48:15', 'For ever and a day.', 'Make a Query', b'0', '697-42-8583', '787-65-4614'),
(13, 'Financial Complaints', '2023-07-18 21:48:16', 'The fool doth think he is wise, but the wise man knows himself to be a fool.', 'Lodge a Complaint', b'0', '747-87-0686', '122-61-2739'),
(14, 'Harassment', '2023-07-18 21:48:18', 'A little more than kin, and less than kind.', 'Lodge a Complaint', b'0', '790-02-1845', '600-24-6275'),
(15, 'Internship Opportunities', '2023-07-18 21:48:19', 'The world is grown so bad, that wrens make prey where eagles dare not perch.', 'Make a Query', b'0', '049-37-2697', '167-46-3413'),
(16, 'Facilities Related', '2023-07-18 21:48:21', 'The lady doth protest too much, methinks.', 'Request a Service', b'0', '600-31-2067', '844-80-7690'),
(17, 'Discrimination', '2023-07-18 21:48:23', 'Conscience is but a word that cowards use, devised at first to keep the strong in awe.', 'Lodge a Complaint', b'0', '250-06-1734', '167-46-3413'),
(18, 'Facilities Related', '2023-07-18 21:48:24', 'How bitter a thing it is to look into happiness through another man\'s eyes!', 'Request a Service', b'0', '609-08-2353', '587-36-1728'),
(19, 'Service Delivery', '2023-07-18 21:48:26', 'An honest tale speeds best, being plainly told.', 'Request a Service', b'0', '250-06-1734', '841-91-0332'),
(20, 'Disciplinary Matters', '2023-07-18 21:48:27', 'And it must follow, as the night the day, thou canst not then be false to any man.', 'Lodge a Complaint', b'0', '852-31-6648', '167-46-3413'),
(21, 'Campus Security', '2023-07-18 21:48:29', 'The world is grown so bad, that wrens make prey where eagles dare not perch.', 'Request a Service', b'0', '790-02-1845', '841-91-0332'),
(22, 'Disciplinary Matters', '2023-07-18 21:48:30', 'I like this place and willingly could waste my time in it.', 'Lodge a Complaint', b'0', '476-78-6726', '841-91-0332'),
(23, 'Immigration Requirements', '2023-07-18 21:48:32', 'The world is grown so bad, that wrens make prey where eagles dare not perch.', 'Make a Query', b'0', '790-02-1845', '652-53-3519'),
(24, 'Language Support', '2023-07-18 21:48:33', 'There is nothing either good or bad, but thinking makes it so.', 'Make a Query', b'0', '697-42-8583', '600-24-6275'),
(25, 'Facilities Related', '2023-07-18 21:48:35', 'An honest tale speeds best, being plainly told.', 'Request a Service', b'0', '600-31-2067', '787-65-4614'),
(26, 'Facilities Related', '2023-07-18 21:48:37', 'The king\'s name is a tower of strength.', 'Request a Service', b'0', '609-08-2353', '841-91-0332'),
(27, 'Facilities Related', '2023-07-18 21:48:38', 'Brevity is the soul of wit.', 'Request a Service', b'0', '049-37-2697', '122-61-2739'),
(28, 'Service Delivery', '2023-07-18 21:48:40', 'True is it that we have seen better days.', 'Request a Service', b'0', '049-37-2697', '122-61-2739'),
(29, 'Scholarship Applications', '2023-07-18 21:48:41', 'The king\'s name is a tower of strength.', 'Make a Query', b'0', '609-08-2353', '167-46-3413'),
(30, 'Campus Security', '2023-07-18 21:48:43', 'An honest tale speeds best, being plainly told.', 'Request a Service', b'0', '852-31-6648', '587-36-1728'),
(31, 'Discrimination', '2023-07-18 21:48:44', 'The fool doth think he is wise, but the wise man knows himself to be a fool.', 'Lodge a Complaint', b'0', '697-42-8583', '787-65-4614'),
(32, 'Service Delivery', '2023-07-18 21:48:46', 'In my mind\'s eye.', 'Request a Service', b'0', '039-64-6787', '841-91-0332'),
(33, 'Campus Security', '2023-07-18 21:48:48', 'How bitter a thing it is to look into happiness through another man\'s eyes!', 'Request a Service', b'0', '476-78-6726', '600-24-6275'),
(34, 'Discrimination', '2023-07-18 21:48:49', 'All the world \'s a stage, and all the men and women merely players. They have their exits and their entrances; And one man in his time plays many parts.', 'Lodge a Complaint', b'0', '039-64-6787', '569-86-4772'),
(35, 'Disciplinary Matters', '2023-07-18 21:48:51', 'The fool doth think he is wise, but the wise man knows himself to be a fool.', 'Lodge a Complaint', b'0', '852-31-6648', '844-80-7690'),
(36, 'Campus Security', '2023-07-18 21:48:52', 'I like this place and willingly could waste my time in it.', 'Request a Service', b'0', '747-87-0686', '122-61-2739'),
(37, 'Campus Security', '2023-07-18 21:48:54', 'The fool doth think he is wise, but the wise man knows himself to be a fool.', 'Request a Service', b'0', '852-31-6648', '122-61-2739'),
(38, 'Discrimination', '2023-07-18 21:48:55', 'Conscience is but a word that cowards use, devised at first to keep the strong in awe.', 'Lodge a Complaint', b'0', '609-08-2353', '569-86-4772'),
(39, 'Financial Complaints', '2023-07-18 21:48:57', 'The world is grown so bad, that wrens make prey where eagles dare not perch.', 'Lodge a Complaint', b'0', '476-78-6726', '587-36-1728'),
(40, 'Facilities Related', '2023-07-18 21:48:59', 'For ever and a day.', 'Request a Service', b'0', '790-02-1845', '569-86-4772'),
(41, 'Service Delivery', '2023-07-18 21:49:00', 'Do you think I am easier to be played on than a pipe?', 'Request a Service', b'0', '039-64-6787', '122-61-2739'),
(42, 'Discrimination', '2023-07-18 21:49:02', 'Rich gifts wax poor when givers prove unkind.', 'Lodge a Complaint', b'0', '250-06-1734', '122-61-2739'),
(43, 'Job Opportunities', '2023-07-18 21:49:03', 'Now is the winter of our discontent.', 'Make a Query', b'0', '747-87-0686', '787-65-4614'),
(44, 'Campus Security', '2023-07-18 21:49:05', 'Doubt that the sun doth move, doubt truth to be a liar, but never doubt I love.', 'Request a Service', b'0', '049-37-2697', '787-65-4614'),
(45, 'Harassment', '2023-07-18 21:49:06', 'Rich gifts wax poor when givers prove unkind.', 'Lodge a Complaint', b'0', '049-37-2697', '573-22-3908'),
(46, 'Financial Complaints', '2023-07-18 21:49:08', 'The king\'s name is a tower of strength.', 'Lodge a Complaint', b'0', '852-31-6648', '587-36-1728'),
(47, 'Campus Security', '2023-07-18 21:49:10', 'The lady doth protest too much, methinks.', 'Request a Service', b'0', '600-31-2067', '841-91-0332'),
(48, 'Harassment', '2023-07-18 21:49:11', 'The king\'s name is a tower of strength.', 'Lodge a Complaint', b'0', '600-31-2067', '573-22-3908'),
(49, 'Disciplinary Matters', '2023-07-18 21:49:13', 'There is nothing either good or bad, but thinking makes it so.', 'Lodge a Complaint', b'0', '250-06-1734', '841-91-0332'),
(50, 'Harassment', '2023-07-18 21:49:15', 'A horse! a horse! my kingdom for a horse!.', 'Lodge a Complaint', b'0', '609-08-2353', '587-36-1728');

-- --------------------------------------------------------

--
-- Table structure for table `Response`
--

CREATE TABLE `Response` (
  `response_id` int(11) NOT NULL,
  `content` varchar(2000) NOT NULL,
  `response_date` datetime NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `message_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Response`
--

INSERT INTO `Response` (`response_id`, `content`, `response_date`, `user_id`, `message_id`) VALUES
(51, 'The fool is indeed wise', '2023-07-18 22:31:51', '122-61-2739', 13);

-- --------------------------------------------------------

--
-- Table structure for table `TemporaryChat`
--

CREATE TABLE `TemporaryChat` (
  `chat_id` varchar(255) NOT NULL,
  `customer_id` varchar(255) DEFAULT NULL,
  `customer_message` varchar(255) DEFAULT NULL,
  `employee_id` varchar(255) DEFAULT NULL,
  `employee_response` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `TemporaryChat`
--

INSERT INTO `TemporaryChat` (`chat_id`, `customer_id`, `customer_message`, `employee_id`, `employee_response`) VALUES
('aD}0U!6wTMs8p2i', '476-78-6726', ':(', '652-53-3519', 'go away');

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `user_id` varchar(255) NOT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `is_staff` bit(1) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`user_id`, `firstname`, `is_staff`, `lastname`, `password`) VALUES
('039-64-6787', 'Caroline', b'0', 'Davis', 'wA@2Xp^i[h'),
('049-37-2697', 'Lynette', b'0', 'Pacocha', 'xL#6tL)3Ko'),
('122-61-2739', 'Sharleen', b'1', 'Krajcik', 'mY]1yo[))R'),
('167-46-3413', 'Val', b'1', 'Senger', 'yE#9I@g0W2'),
('250-06-1734', 'Elliot', b'0', 'Daniel', 'gH}1iS~EHD'),
('476-78-6726', 'Ryan', b'0', 'Kessler', 'kP&55rZHgR'),
('569-86-4772', 'Raguel', b'1', 'Leuschke', 'lL{7@*U{UQ'),
('573-22-3908', 'Lenard', b'1', 'Beer', 'mI@7SeF15t'),
('587-36-1728', 'Richelle', b'1', 'Walter', 'dV(5WUXQ^C'),
('600-24-6275', 'Lynwood', b'1', 'Bode', 'fT&11B&SAn'),
('600-31-2067', 'Coleman', b'0', 'Boyer', 'fF$7yT)uSh'),
('609-08-2353', 'Kasey', b'0', 'Tillman', 'cJ[5{KH9r)'),
('652-53-3519', 'Coral', b'1', 'Lowe', 'aK}90XFV)&'),
('697-42-8583', 'Erasmo', b'0', 'Auer', 'zM(6ex(SBN'),
('747-87-0686', 'Christie', b'0', 'Morissette', 'xJ!7BR([x8'),
('787-65-4614', 'Doria', b'1', 'Pollich', 'vI(5#C]IH6'),
('790-02-1845', 'Yer', b'0', 'Brakus', 'cT[2V}CWK2'),
('841-91-0332', 'Herbert', b'1', 'Watsica', 'pN{038v#v}'),
('844-80-7690', 'Davina', b'1', 'Konopelski', 'sT!6Djzor^'),
('852-31-6648', 'Lenora', b'0', 'Jakubowski', 'wJ&5UJ~()&'),
('samuel', 'samuel', b'1', 'Matheson', 'samuel');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Customer`
--
ALTER TABLE `Customer`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `Employee`
--
ALTER TABLE `Employee`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `hibernate_sequences`
--
ALTER TABLE `hibernate_sequences`
  ADD PRIMARY KEY (`sequence_name`);

--
-- Indexes for table `Message`
--
ALTER TABLE `Message`
  ADD PRIMARY KEY (`message_id`),
  ADD KEY `FKaclfiecdvtu6b6g3ohdnwfiux` (`customer_id`),
  ADD KEY `FKhn2lbmk799b9grmah29qu6jgk` (`employee_id`);

--
-- Indexes for table `Response`
--
ALTER TABLE `Response`
  ADD PRIMARY KEY (`response_id`),
  ADD KEY `FKcrg7op3k1ogamipshshb1ne5d` (`message_id`);

--
-- Indexes for table `TemporaryChat`
--
ALTER TABLE `TemporaryChat`
  ADD PRIMARY KEY (`chat_id`);

--
-- Indexes for table `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`user_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Customer`
--
ALTER TABLE `Customer`
  ADD CONSTRAINT `FK6y0rqloalxi6r6lpoqtfohp9i` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`);

--
-- Constraints for table `Employee`
--
ALTER TABLE `Employee`
  ADD CONSTRAINT `FKcbmmnapuondn8kfyqrs762wle` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`);

--
-- Constraints for table `Message`
--
ALTER TABLE `Message`
  ADD CONSTRAINT `FKaclfiecdvtu6b6g3ohdnwfiux` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`user_id`),
  ADD CONSTRAINT `FKhn2lbmk799b9grmah29qu6jgk` FOREIGN KEY (`employee_id`) REFERENCES `Employee` (`user_id`);

--
-- Constraints for table `Response`
--
ALTER TABLE `Response`
  ADD CONSTRAINT `FKcrg7op3k1ogamipshshb1ne5d` FOREIGN KEY (`message_id`) REFERENCES `Message` (`message_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
