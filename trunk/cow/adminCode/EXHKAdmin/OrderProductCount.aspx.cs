using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using MySql.Data.MySqlClient;
using System.Configuration;

public partial class OrderProductCount : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            ddlStatus.DataSource = CommonHelper.GetAllOrderIDandName();
            ddlStatus.DataBind();
            ddlStatus.Items.Insert(0, new ListItem("所有", "ALL"));
        }
    }

    private void BindData()
    {
        string strSql = "select tro.id,tro.status,tro.memo,tro.total_price,COALESCE(m.name,'未注册用户') member_name,d.name delivery_name,p.name pay_name " +
            ",tro.gmt_created order_time " +
            " from delivery d,payment p,(select * from trade_order where 0=0 ";

        if (ddlStatus.SelectedValue != "ALL")
        {
            strSql += " and status='" + ddlStatus.SelectedValue + "'";

        }
        if (txtMemberLoginID.Text.Trim() != "")
        {
            strSql += " and member_id =(select id from member where login_id = '" + txtMemberLoginID.Text.Trim() + "') ";
        }

        strSql += ") tro left join member m on tro.member_id=m.id where tro.delivery_id=d.id and tro.pay_id=p.id";

        DataSet ds = MySqlHelper.ExecuteDataset(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
            strSql);

        GridView1.DataSource = ds;
        GridView1.DataBind();

    }

    protected void btnFilte_Click(object sender, EventArgs e)
    {
        GridView1.PageIndex = 0;
        BindData();
    }

    protected void GridView1_PageIndexChanging(object sender, GridViewPageEventArgs e)
    {
        GridView1.PageIndex = e.NewPageIndex;
        BindData();
    }

    protected void GridView1_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        string id = e.CommandArgument.ToString();
        MySqlParameter p = new MySqlParameter("@status", MySqlDbType.String);
        if (e.CommandName == "PAID")
        {
            p.Value = "PAID";
        }
        else if (e.CommandName == "DELIVERED")
        {
            p.Value = "DELIVERED";
        }
        else if (e.CommandName == "SUCCESS")
        {
            p.Value = "SUCCESS";
        }
        string strSql = "update trade_order set status = @status where id=@id";
        MySqlHelper.ExecuteNonQuery(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
            strSql, p, new MySqlParameter("@id", id));
        BindData();
    }
}
