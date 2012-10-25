package Sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import jp.nextengine.api.sdk.*;

import java.util.List;

public class SampleServlet extends HttpServlet {

	protected static final String CLIENT_ID = "NPhnUwMY61OuQV";
	protected static final String CLIENT_SECRET = "qgMRu4GsxmV1JLF8hjkft3UYrePiWXB9Q7DzAlwd";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		/* HTML 出力用 PrintWriter */
		PrintWriter out = response.getWriter();

		HashMap<String, Object> result = null;
		List<HashMap<String, String>> companies = null;
		HashMap<String, String> company = null;
		List<HashMap<String, String>> rows = null;

		try {
			NeApiClient client = new NeApiClient(request, response,
					SampleServlet.CLIENT_ID, SampleServlet.CLIENT_SECRET,
					"https://localhost:8443/Sample/SampleServlet");
			result = client.apiExecute("/api_v1_login_company/info");
			companies = (List<HashMap<String, String>>) result.get("data");
			company = companies.get(0);
			result = client.apiExecuteNoRequiredLogin("/api_app/company");

			HashMap<String, String> params = new HashMap();
			params.put(
					"placeholder_sql",
					"SELECT '__kigyo_code__' AS kigyo_code,t_mst_syohin_kihon.* FROM ne_userXXX.t_mst_syohin_kihon LIMIT 1");
			params.put("is_select", "1");
			params.put("is_need_csv_header", "1");
			params.put("data_type", "csv");
			result = client.apiExecute("/api_v1_internal_sql/execute", params);
			rows = (List<HashMap<String, String>>) result.get("data");
			result = client.apiExecute("/api_v1_internal_sql/execute", params);
			rows = (List<HashMap<String, String>>) result.get("data");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* HTML出力 */
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Hello!</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<H1>");
		out.println("Hello!");
		out.println(company.get("company_id"));
		out.println(result.toString());
		out.println("<H1>");
		out.println("</body>");
		out.println("</html>");
		out.close();

	}
}