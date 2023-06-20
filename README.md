
# AP-group-2-project

## Members

- [Samuel A. Matheson](https://github.com/AyoXavi)
- [Shianne Baugh](https://github.com/shibaugh)
- [Shereka Miller](https://github.com/Reka-16)
- [Pete Aris](https://github.com/Patoaris)
- [Xavier Kirlew](https://github.com/AyoXavi)


## Description
Flow has experienced a substantial increase in service requests and a growing number of internet-related inquiries. To address this, the manager requires an issue logging system capable of capturing all **customer requests**, **complaints**, and **queries**. When an issue is received, a customer service representative will log it and either provide a solution or assign/schedule a service technician to resolve the problem. 

Your task is to develop a TCP/IP socket-based Client/Server application that facilitates the described scenario. The Client application should send requests to the server application since only the server can establish connections to the database. The client application should have a graphical user interface (GUI) to enhance the user experience, while the server application does not necessarily require a GUI but may include graphical components if desired. Your system must capture all data about **Customers**, **Customer - Representatives**, **Technicians** and **Issues**.


## System Functionalities

### Authentication:
- Customers should be able to log on to the system using their **Customer ID number** and
**password** to gain access to the dashboard.
- Representatives and Technicians (Employees) should be able to log on to the system using
their **Staff ID** and **password** to gain access to the dashboard.

### Customer Services
 - On the Dashboard, **Customers** should be able to choose the service they would like **assistance** with, i.e.,
	 - to lodge a complaint
	 - make a query
	 - request service.
 - They should also be able to register the nature and details of their complaint, so that a Representative can access it later (Be creative and come up with at least three categories of complaints);
 - Customers should be able to view all past complaints in a list. For each complaint in the list, the last response date and who provided the response, should be shown.
 - Customers should also be able to view a specific complaint and all its associated responses.

### Employee Services
- **Representatives** should be able to view a list of services on the dashboard along with the number of resolved and outstanding complaints.
- **Representatives** should be able to view all Customer complaints relating to a particular service, e.g., All complaints relating to Broadband.
- **Representatives** should be able to assign a complaint to a technician.
- **Technicians** should be able to view a particular Customer complaint and the details relating to that issue, so that they may prepare a response, including proposed date of visit.
- When viewing specific Customers’ complaints, a Representative should see Customers’ details to include:
	- Customer ID
	- Name (first name and last name)
	- Email address
	- Contact number
	- Type of issue
	- Details of the issue.

### Live Chat
- **Technicians** should be able to indicate their availability for a live chat session, so that Customers can initiate a live chat session.
- **Technicians** should be able to participate in a live chat session with Customers, to address their issues.
- **Customers** should be able to initiate a live chat with a **Representative**, to address their issue.
