/****************************************************************
 *   ____            _                        ___ __  __ _____  *
 *  / ___|___  _ __ | |    __ _ _ __   __ _  |_ _|  \/  | ____| *
 * | |   / _ \| '_ \| |   / _` | '_ \ / _` |  | || |\/| |  _|   *
 * | |__| (_) | | | | |__| (_| | | | | (_| |  | || |  | | |___  *
 *  \____\___/|_| |_|_____\__,_|_| |_|\__, | |___|_|  |_|_____| *
 *                                    |___/                     *
 *                                                              *
 * Copyright © 2015, Christian Bryce Alexander                  *
 ****************************************************************/
package net.alexanderdev.clime.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * @author Christian Bryce Alexander
 * @since Aug 13, 2015, 10:35:06 PM
 */
public class XMLIO {
	public static Document read(String loc, boolean rel) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		Document xml;

		if (rel) {
			xml = builder.parse(XMLIO.class.getResourceAsStream(loc));
		} else {
			xml = builder.parse(new File(loc));
		}

		xml.normalize();

		return xml;
	}

	public static Document create() throws ParserConfigurationException {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		Document xml = builder.newDocument();

		return xml;
	}

	public static void write(Document doc, String loc) throws Exception {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();

		DOMSource source = new DOMSource(doc);

		StreamResult result = new StreamResult(new File(loc));

		transformer.transform(source, result);
	}
}