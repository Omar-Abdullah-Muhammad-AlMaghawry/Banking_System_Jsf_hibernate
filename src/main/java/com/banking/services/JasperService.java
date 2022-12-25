package com.banking.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;

public class JasperService {
	public static ServletContext getContext() {
		return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	private static Connection getDataSourceConnection(String dataSourceName, String username, String password)
			throws Exception {
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup(dataSourceName);
		return ds.getConnection();
	}

	private static Connection getConnection() throws Exception {
		return getDataSourceConnection("java:comp/env/jdbc/bankDB", "omar", "password");///
	}

	public static void runReport(String reportPath, Map param, List<?> dataSource) throws Exception {
		Connection conn = null; // opens a jdbc connection
		final List<byte[]> data = new ArrayList<byte[]>();
		try {
			HttpServletResponse response = getResponse();
			response.setHeader("Cache-Control", "max-age=0");
//	        response.addHeader("Content-disposition", "attachment; filename=/Reports/jsfReporte.pdf");
			response.setContentType("application/pdf");
			FacesContext.getCurrentInstance().responseComplete();
			ServletOutputStream outStream = response.getOutputStream();
			ServletContext context = getContext();
			InputStream fileStream = context.getResourceAsStream("/Reports/" + reportPath);
			JasperReport template = (JasperReport) JRLoader.loadObject(fileStream);
			template.setWhenNoDataType(WhenNoDataTypeEnum.NO_DATA_SECTION);
			conn = getConnection();
			JasperPrint print = JasperFillManager.fillReport(template, param, conn);
			ByteArrayOutputStream byteArrOutStr = new ByteArrayOutputStream();
			File f = File.createTempFile("test123", ".pdf");
			JasperExportManager.exportReportToPdfStream(print, byteArrOutStr);
			outStream.write(byteArrOutStr.toByteArray());
			outStream.flush();
			outStream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			///TODO throw error not print it
			e.printStackTrace();

		} finally {
			// it's your responsibility to close the connection, don't forget it!
			if (conn != null) {
				conn.close();
			}
		}
	}
}
