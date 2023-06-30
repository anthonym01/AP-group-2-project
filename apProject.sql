-- phpMyAdmin SQL Dump
-- version 5.0.4deb2+deb11u1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 28, 2023 at 10:04 PM
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
-- Database: `apProject`
--

-- --------------------------------------------------------

--
-- Table structure for table `complaint`
--

CREATE TABLE `complaint` (
  `type` varchar(50) NOT NULL COMMENT 'Type of complaint',
  `id` int(45) NOT NULL,
  `date_created` date NOT NULL DEFAULT current_timestamp(),
  `created_by` varchar(50) NOT NULL COMMENT 'created_by(customer.ID)',
  `Title` varchar(200) NOT NULL,
  `Description` varchar(9999) NOT NULL,
  `assigned_to` int(15) NOT NULL COMMENT 'Employee staff ID_number'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `complaint_reply`
--

CREATE TABLE `complaint_reply` (
  `ID` int(45) NOT NULL,
  `BelongsTo` int(45) NOT NULL COMMENT 'BelongsTo(complaints.ID)',
  `created` date NOT NULL,
  `Created_by` int(15) NOT NULL COMMENT 'Created_by(Employee.ID)',
  `text` varchar(999) NOT NULL COMMENT 'Body of the reply'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `ID` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Contact_number` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`ID`, `password`, `first_name`, `last_name`, `Email`, `Contact_number`) VALUES
('SamuelM', '5744801', 'Samuel', 'Matheson', 'samuelmatheson15@gmail.com', '8767818292'),
('test', 'testhash', '0', '0', '0', '');

-- --------------------------------------------------------

--
-- Table structure for table `Employee`
--

CREATE TABLE `Employee` (
  `type` int(2) NOT NULL,
  `ID_number` int(15) NOT NULL,
  `password` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `Email` varchar(60) NOT NULL,
  `Contact_number` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `queries`
--

CREATE TABLE `queries` (
  `ID` int(50) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` varchar(999) NOT NULL,
  `created` date NOT NULL,
  `created_by` varchar(50) NOT NULL COMMENT 'created_by(customer.ID)',
  `assigned_to` int(45) NOT NULL COMMENT 'assigned_to(staff_ID_number)',
  `staff_Reply` varchar(999) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `request`
--

CREATE TABLE `request` (
  `ID` int(45) NOT NULL,
  `Service_type` varchar(50) NOT NULL,
  `description` varchar(999) NOT NULL,
  `created` date NOT NULL DEFAULT current_timestamp(),
  `created_by` varchar(50) NOT NULL COMMENT 'created_by(customerID)',
  `assigned_to` int(15) NOT NULL COMMENT 'assigned_to(staff_ID_number)',
  `resolution_target` date NOT NULL,
  `resolution_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `complaint`
--
ALTER TABLE `complaint`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_by` (`created_by`,`assigned_to`),
  ADD KEY `assigned_to` (`assigned_to`);

--
-- Indexes for table `complaint_reply`
--
ALTER TABLE `complaint_reply`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `BelongsTo` (`BelongsTo`,`Created_by`),
  ADD KEY `Created_by` (`Created_by`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `Employee`
--
ALTER TABLE `Employee`
  ADD PRIMARY KEY (`ID_number`);

--
-- Indexes for table `queries`
--
ALTER TABLE `queries`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `created_by` (`created_by`),
  ADD KEY `assigned_to` (`assigned_to`);

--
-- Indexes for table `request`
--
ALTER TABLE `request`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `created_by` (`created_by`,`assigned_to`),
  ADD KEY `assigned_to` (`assigned_to`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `complaint`
--
ALTER TABLE `complaint`
  ADD CONSTRAINT `complaint_ibfk_1` FOREIGN KEY (`assigned_to`) REFERENCES `Employee` (`ID_number`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `complaint_ibfk_2` FOREIGN KEY (`created_by`) REFERENCES `customers` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `complaint_ibfk_3` FOREIGN KEY (`id`) REFERENCES `complaint_reply` (`BelongsTo`);

--
-- Constraints for table `complaint_reply`
--
ALTER TABLE `complaint_reply`
  ADD CONSTRAINT `complaint_reply_ibfk_1` FOREIGN KEY (`Created_by`) REFERENCES `Employee` (`ID_number`);

--
-- Constraints for table `Employee`
--
ALTER TABLE `Employee`
  ADD CONSTRAINT `Employee_ibfk_1` FOREIGN KEY (`ID_number`) REFERENCES `queries` (`assigned_to`);

--
-- Constraints for table `queries`
--
ALTER TABLE `queries`
  ADD CONSTRAINT `queries_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `customers` (`ID`);

--
-- Constraints for table `request`
--
ALTER TABLE `request`
  ADD CONSTRAINT `request_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `customers` (`ID`),
  ADD CONSTRAINT `request_ibfk_2` FOREIGN KEY (`assigned_to`) REFERENCES `Employee` (`ID_number`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
