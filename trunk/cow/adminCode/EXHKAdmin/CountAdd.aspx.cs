using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using MySql.Data.MySqlClient;
using System.Configuration;

public partial class CountAdd : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

    }

    private void BindData()
    {
        string strSql = "select i.*,c.name as cname from category c,(" +
            " SELECT id,name,cover_img_name,view,type_id,market_price,my_price FROM product_info  " +
            " where name like '%" + txtNameKey.Text.Trim() + "%' " +
            " order by gmt_modified desc" +
            " ) i where i.type_id=c.id ";

        DataSet ds = MySqlHelper.ExecuteDataset(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
            strSql);

        GridView1.DataSource = ds;
        GridView1.DataBind();
    }

    protected void btnFilte_Click(object sender, EventArgs e)
    {
        if (txtNameKey.Text.Trim() == "")
        {
            ClientScript.RegisterStartupScript(this.GetType(), "warn", "<script>alert('请输入关键字');</script>");
        }
        BindData();
    }
    protected void GridView1_SelectedIndexChanged(object sender, EventArgs e)
    {
        MultiView1.ActiveViewIndex = 1;
        string id = GridView1.SelectedValue.ToString();

        string strSql = "select name,sizes from product_info where id=" + id;
        DataRow dr = MySqlHelper.ExecuteDataRow(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
               strSql);
        string sizes = dr["sizes"].ToString();
        hdfProductName.Value = dr["name"].ToString();
        listSize.DataSource = sizes.Split(',');
        listSize.DataBind();

        strSql = "select id,color_name from product_color where product_id=" + id;
        DataSet ds = MySqlHelper.ExecuteDataset(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
               strSql);
        listColor.DataSource = ds;
        listColor.DataTextField = "color_name";
        listColor.DataValueField = "id";
        listColor.DataBind();

    }

    protected void btnAdd_Click(object sender, EventArgs e)
    {
        labSize.Text = listSize.SelectedValue;
        labColor.Text = listColor.SelectedItem.Text;
        hdfColorId.Value = listColor.SelectedItem.Value;
        MultiView1.ActiveViewIndex = 2;

        string strSql = "select id,count from product_count where color_id=@color_id and size=@size";
        DataRow dr = MySqlHelper.ExecuteDataRow(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                 strSql, new MySqlParameter("@color_id", hdfColorId.Value), new MySqlParameter("@size", labSize.Text));

        if (dr != null)
        {
            hdfOldID.Value = dr["id"].ToString();
            txtCount.Text = dr["count"].ToString();
        }
    }
    protected void btnSure_Click(object sender, EventArgs e)
    {
        string id = GridView1.SelectedValue.ToString();
        uint count = 0;
        if (!uint.TryParse(txtCount.Text.Trim(), out count))
        {
            ClientScript.RegisterStartupScript(this.GetType(), "warn", "<script>alert('您填写的数量错误，修改无效！');</script>");
            return;
        }
        string strSql;
        if (hdfOldID.Value == "")
        {
            strSql = "insert into product_count(color_id,product_name,size,count,gmt_created,gmt_modified) " +
               " values(@color_id,@product_name,@size,@count,now(),now())";
            MySqlHelper.ExecuteNonQuery(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                strSql, new MySqlParameter("@color_id", hdfColorId.Value), new MySqlParameter("@product_name", hdfProductName.Value),
                 new MySqlParameter("@size", labSize.Text), new MySqlParameter("@count", count));

            MultiView1.ActiveViewIndex = 0;
            ClientScript.RegisterStartupScript(this.GetType(), "success", "<script>alert('数据已成功添加了！');</script>");
        }
        else
        {
            //TODO 已有情况处理
            strSql = "update product_count set count=@count where id=@id";
            MySqlHelper.ExecuteNonQuery(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                strSql, new MySqlParameter("@count", count), new MySqlParameter("@id", hdfOldID.Value));
            MultiView1.ActiveViewIndex = 0;
            ClientScript.RegisterStartupScript(this.GetType(), "success", "<script>alert('数据已成功添加了！');</script>");
        }
    }
    protected void btnCancel_Click(object sender, EventArgs e)
    {
        MultiView1.ActiveViewIndex = 0;

    }
}
