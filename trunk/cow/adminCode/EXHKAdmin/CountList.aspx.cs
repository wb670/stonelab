using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using MySql.Data.MySqlClient;
using System.Configuration;

public partial class CountList : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
        }
    }

    private void BindData()
    {
        string strSql = "select pcn.id,pcn.product_name,pcl.color_name,pcn.size,pcn.count" +
            " from product_count pcn,product_color pcl where";
        string strQuery = " pcn.color_id=pcl.id";
        if (txtProductName.Text.Trim() != "")
        {
            strQuery = " pcn.product_name like '%" + txtProductName.Text.Trim() + "%' and" + strQuery;
        }
        if (chkGroup.Checked)
        {
            strSql = "select '#' id,pcn.product_name,'all' color_name,'all' size,sum(pcn.count) count" +
            " from product_count pcn,product_color pcl where" + strQuery + " group by pcn.product_name";
        }
        else
        {
            strSql += strQuery;
        }

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
        if (e.CommandName == "CHANGE")
        {
            if (id == "#")
            {
                ClientScript.RegisterStartupScript(this.GetType(), "warn", "<script>alert('汇总情况下无法修改数量！');</script>");
                return;
            }
            else
            {
                uint count = 0;
                if (!uint.TryParse(hdfCountValue.Value, out count))
                {
                    ClientScript.RegisterStartupScript(this.GetType(), "warn", "<script>alert('您填写的数量错误，修改无效！');</script>");
                    return;
                }
                string strSql = "update product_count set count=@count where id=@id";
                MySqlHelper.ExecuteNonQuery(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                    strSql, new MySqlParameter("@count", count), new MySqlParameter("@id", id));
                BindData();
            }
        }

    }
}
