using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using MySql.Data.MySqlClient;
using System.Configuration;

public partial class AlliedMemberCount : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            labCount.Text = MySqlHelper.ExecuteScalar(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                "select count(*) from allied_member").ToString();
            BindData();
        }

    }
    private void BindData()
    {
        string strSql = "select id,login_id,name,telphone,mobile,fax,qq,msn,identity_card,site_address,site_name,site_type,site_kind,enterprise_name FROM allied_member ";

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
