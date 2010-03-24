using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using MySql.Data.MySqlClient;
using System.Configuration;

public partial class MemberCount : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            labCount.Text = MySqlHelper.ExecuteScalar(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                "select count(*) from member").ToString();
            BindData();
        }

    }
    private void BindData()
    {
        string strSql = "select id,login_id,name,sex,country,province,city,area,telphone,mobile FROM member ";

        DataSet ds = MySqlHelper.ExecuteDataset(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
            strSql);

        GridView1.DataSource = ds;
        GridView1.DataBind();

    }
    protected void GridView1_PageIndexChanging(object sender, GridViewPageEventArgs e)
    {
        GridView1.PageIndex = e.NewPageIndex;
        BindData();
    }


}
