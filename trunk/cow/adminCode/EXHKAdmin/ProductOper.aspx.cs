using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using MySql.Data.MySqlClient;
using System.Configuration;

public partial class ProductOper : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void ddlCatagoryG1_SelectedIndexChanged(object sender, EventArgs e)
    {
        BindCategoryG2();
    }

    private void BindCategoryG2()
    {
        ddlCatagoryG2.Items.Clear();
        if (ddlCatagoryG1.SelectedValue != "-1")
        {
            DataSet ds = MySqlHelper.ExecuteDataset(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                "select id,name from category where parent_id=@parent_id",
                new MySqlParameter("@parent_id", ddlCatagoryG1.SelectedValue));
            if (ds.Tables[0].Rows.Count != 0)
            {
                ddlCatagoryG2.DataSource = ds;
                ddlCatagoryG2.DataBind();
            }
        }
        ddlCatagoryG2.Items.Insert(0, new ListItem("所有", "-1"));
    }

    private void BindData()
    {
        string strSql = "select i.*,c.name as cname,po.type ptype from category c,(" +
            " SELECT id,name,cover_img_name,view,type_id,market_price,my_price FROM product_info  where 0=0 ";
        List<MySqlParameter> pList = new List<MySqlParameter>();

        if (ddlCatagoryG1.SelectedValue != "-1" || ddlCatagoryG2.SelectedValue != "-1")
        {

            if (ddlCatagoryG2.SelectedValue == "-1")
            {
                if (ddlCatagoryG2.Items.Count == 1)
                    strSql += " and type_id=" + ddlCatagoryG1.SelectedValue;
                else
                    strSql += " and type_id in (select id from category where parent_id=" + ddlCatagoryG1.SelectedValue + ")";
            }
            else
                strSql += " and type_id=" + ddlCatagoryG2.SelectedValue;
        }

        if (txtNameKey.Text.Trim() != "")
        {
            strSql += " and name like '%" + txtNameKey.Text.Trim() + "%' ";
        }

        strSql += " order by gmt_modified desc" +
            " ) i left join (select product_id,GROUP_CONCAT(case type when 'RECOMMEND' then '推荐' when 'HOT' then '热卖' when 'NEW' then '最新' ELSE 'NULL' END) type from product_operator group by product_id) po " +
            "on i.id=po.product_id where i.type_id=c.id ";

        DataSet ds = MySqlHelper.ExecuteDataset(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
            strSql, pList.ToArray());

        GridView1.DataSource = ds;
        GridView1.DataBind();

    }

    protected void Page_PreRender(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            ddlCatagoryG1.DataBind();
            BindCategoryG2();
            // BindData();
        }
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
        if (e.CommandName == "RECOMMEND" || e.CommandName == "HOT" || e.CommandName == "NEW")
        {
            object o = MySqlHelper.ExecuteScalar(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                   "SELECT id FROM product_operator where product_id=" + e.CommandArgument + " and type='" + e.CommandName + "'");
            if (o == null)
            {
                MySqlHelper.ExecuteNonQuery(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                   "insert into product_operator(product_id,type,gmt_created,gmt_modified) values(" +
                   e.CommandArgument + ",'" + e.CommandName + "',now(),now())");
                ClientScript.RegisterStartupScript(Page.GetType(), "Success", "<script>alert('该产品操作成功!')</script>");
            }
            else
            {
                ClientScript.RegisterStartupScript(Page.GetType(), "Error", "<script>alert('该产品已被为推广为该项了!')</script>");
            }
        }
        else if (e.CommandName == "CANCELRECOMMEND" || e.CommandName == "CANCELHOT" || e.CommandName == "CANCELNEW")
        {
            object o = MySqlHelper.ExecuteScalar(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                   "SELECT id FROM product_operator where product_id=" + e.CommandArgument + " and type='" + e.CommandName.Remove(0,6) + "'");
            if (o == null)
            {
                ClientScript.RegisterStartupScript(Page.GetType(), "Error", "<script>alert('该产品未被推广过!')</script>");
            }
            else
            {
                 MySqlHelper.ExecuteNonQuery(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                   "delete from product_operator where id=" + o.ToString());
                ClientScript.RegisterStartupScript(Page.GetType(), "Success", "<script>alert('该产品取消推广操作成功!')</script>");
           }
        }
        BindData();

    }
}
