package ecashie.model.appdetails;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ecashie.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppDetails
{
	private static InputStream AppDetailsXML = MainApp.class.getResourceAsStream("AppDetails.xml");

	public static String DevelopmentBegin = "2017";
	public static String VersionNumber = "";
	public static String ReleaseDate = "";

	public static void read() throws IllegalArgumentException, ParserConfigurationException, SAXException, IOException
	{
		Document document = prepareDocument();
		
		readOutContributors(document);

		readOutAppVersions(document);

		initializeGeneralAppDetails();

		readOutImageLicenses(document);

		readOutImageDesigner(document);

		readOutImageSources(document);

	}

	private static Document prepareDocument() throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

		Document document = documentBuilder.parse(AppDetailsXML);

		return document;
	}

	private static void readOutContributors(Document document)
	{
		NodeList contributorNodeList = document.getElementsByTagName("Contributor");

		for (int i = 0; i < contributorNodeList.getLength(); i++)
		{
			Element contributorElement = (Element) contributorNodeList.item(i);

			createNewContributor(contributorElement);
		}
	}

	private static void createNewContributor(Element contributorElement)
	{
		String name = contributorElement.getAttribute("name");
		String roles = contributorElement.getAttribute("roles");

		new Contributor(name, roles);
	}

	private static void readOutAppVersions(Document document)
	{
		NodeList appVersionNodeList = document.getElementsByTagName("AppVersion");

		for (int i = 0; i < appVersionNodeList.getLength(); i++)
		{
			Element appVersionElement = (Element) appVersionNodeList.item(i);

			ObservableList<String> newFunctionsList = readOutFunctions(appVersionElement);
			ObservableList<String> importantChangesList = readOutImportantChanges(appVersionElement);
			ObservableList<String> bugFixesList = readOutBugFixes(appVersionElement);

			createNewAppVersion(appVersionElement, newFunctionsList, importantChangesList, bugFixesList);
		}
	}

	private static void initializeGeneralAppDetails()
	{
		AppVersion latestAppVersion = AppVersion.AppVersionList.get(AppVersion.AppVersionList.size() - 1);

		VersionNumber = latestAppVersion.getVersionNumber();
		ReleaseDate = latestAppVersion.getVersionReleaseDate();
	}

	private static ObservableList<String> readOutFunctions(Element appVersionElement)
	{
		Element newFunctionsElement = (Element) appVersionElement.getElementsByTagName("NewFunctions").item(0);
		NodeList newFunctionsNodeList = newFunctionsElement.getElementsByTagName("NewFunction");
		ObservableList<String> newFunctionsList = FXCollections.observableArrayList();

		for (int j = 0; j < newFunctionsNodeList.getLength(); j++)
		{
			Element newFunctionElement = (Element) newFunctionsNodeList.item(j);
			String value = newFunctionElement.getAttribute("value");

			if (!value.isEmpty())
			{
				newFunctionsList.add(value);
			}
		}

		return newFunctionsList;
	}

	private static ObservableList<String> readOutImportantChanges(Element appVersionElement)
	{
		Element importantChangesElement = (Element) appVersionElement.getElementsByTagName("ImportantChanges").item(0);
		NodeList importantChangesNodeList = importantChangesElement.getElementsByTagName("ImportantChange");
		ObservableList<String> importantChangesList = FXCollections.observableArrayList();

		for (int j = 0; j < importantChangesNodeList.getLength(); j++)
		{
			Element importantChangeElement = (Element) importantChangesNodeList.item(j);
			String value = importantChangeElement.getAttribute("value");

			if (!value.isEmpty())
			{
				importantChangesList.add(value);
			}
		}

		return importantChangesList;
	}

	private static ObservableList<String> readOutBugFixes(Element appVersionElement)
	{
		Element bugFixesElement = (Element) appVersionElement.getElementsByTagName("BugFixes").item(0);
		NodeList bugFixesNodeList = bugFixesElement.getElementsByTagName("BugFix");
		ObservableList<String> bugFixesList = FXCollections.observableArrayList();

		for (int j = 0; j < bugFixesNodeList.getLength(); j++)
		{
			Element bugFixElement = (Element) bugFixesNodeList.item(j);
			String value = bugFixElement.getAttribute("value");

			if (!value.isEmpty())
			{
				bugFixesList.add(value);
			}
		}

		return bugFixesList;
	}

	private static void createNewAppVersion(Element appVersionElement, ObservableList<String> newFunctionsList,
			ObservableList<String> importantChangesList, ObservableList<String> bugFixesList)
	{
		String versionNumber = appVersionElement.getAttribute("VersionNumber");
		String versionReleaseDate = appVersionElement.getAttribute("VersionReleaseDate");

		new AppVersion(versionNumber, versionReleaseDate, newFunctionsList, importantChangesList, bugFixesList);
	}

	private static void readOutImageLicenses(Document document)
	{
		NodeList imageLicenseNodeList = document.getElementsByTagName("ImageLicense");

		for (int i = 0; i < imageLicenseNodeList.getLength(); i++)
		{
			Element imageLicenseElement = (Element) imageLicenseNodeList.item(i);

			createNewImageLicense(imageLicenseElement);
		}
	}

	private static void createNewImageLicense(Element imageLicenseElement)
	{
		String identifier = imageLicenseElement.getAttribute("identifier");
		String text = imageLicenseElement.getAttribute("text");
		String source = imageLicenseElement.getAttribute("source");

		new ImageLicense(identifier, text, source);
	}

	private static void readOutImageDesigner(Document document)
	{
		NodeList imageDesignerNodeList = document.getElementsByTagName("ImageDesigner");

		for (int i = 0; i < imageDesignerNodeList.getLength(); i++)
		{
			Element imageDesignerElement = (Element) imageDesignerNodeList.item(i);

			createNewImageDesigner(imageDesignerElement);
		}
	}

	private static void createNewImageDesigner(Element imageDesignerElement)
	{
		String identifier = imageDesignerElement.getAttribute("identifier");
		String designer = imageDesignerElement.getAttribute("designer");
		String homepage = imageDesignerElement.getAttribute("homepage");

		new ImageDesigner(identifier, designer, homepage);
	}

	private static void readOutImageSources(Document document)
	{
		NodeList imageSourceNodeList = document.getElementsByTagName("ImageSource");

		for (int i = 0; i < imageSourceNodeList.getLength(); i++)
		{
			Element imageSourceElement = (Element) imageSourceNodeList.item(i);

			createNewImageSource(imageSourceElement);
		}
	}

	private static void createNewImageSource(Element imageSourceElement)
	{
		String identifier = imageSourceElement.getAttribute("identifier");
		String designerID = imageSourceElement.getAttribute("designerID");
		String licenseID = imageSourceElement.getAttribute("licenseID");
		String source = imageSourceElement.getAttribute("source");
		String comment = imageSourceElement.getAttribute("comment");

		new ImageSource(identifier, designerID, licenseID, source, comment);
	}
}
