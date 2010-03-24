using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using MySql.Data.MySqlClient;
using System.Configuration;

public partial class ProductList : System.Web.UI.Page
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
        string strSql = "select i.*,c.name as cname from category c,(" +
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
            " ) i where i.type_id=c.id ";

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
            BindData();
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
    protected void GridView1_RowDeleting(object sender, GridViewDeleteEventArgs e)
    {
        string id = ((GridView)sender).DataKeys[e.RowIndex].Value.ToString();
        MySqlParameter pId = new MySqlParameter("@id", id);

        MySqlConnection conn = new MySqlConnection(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
        conn.Open();
        MySqlTransaction tran = conn.BeginTransaction();

        try
        {
            string strSql = "delete from product_washing where product_id=@id";
            MySqlHelper.ExecuteNonQuery(conn, strSql, pId);

            strSql = "select img_name,img_group_id from product_color where product_id=@id";
            MySqlDataReader reader = MySqlHelper.ExecuteReader(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                strSql, pId);
            while (reader.Read())
            {
                CommonHelper.DeleteImgFile(reader["img_name"].ToString());

                if (reader["img_group_id"].ToString() != "")
                {
                    strSql = "select img_name from img where group_id=" + reader["img_group_id"].ToString();
                    foreach (DataRow dr in MySqlHelper.ExecuteDataset(conn, strSql).Tables[0].Rows)
                    {
                        CommonHelper.DeleteImgFile(reader["img_name"].ToString());
                    }

                    strSql = "delete from img where group_id=" + reader["img_group_id"].ToString();
                    MySqlHelper.ExecuteNonQuery(conn, strSql);

                    strSql = "delete from img_group where id=" + reader["img_group_id"].ToString();
                    MySqlHelper.ExecuteNonQuery(conn, strSql);
                }
            }
            reader.Close();

            strSql = "delete from product_color where product_id=@id";
            MySqlHelper.ExecuteNonQuery(conn, strSql, pId);


            strSql = "delete from product_info where id=@id";
            MySqlHelper.ExecuteNonQuery(conn, strSql, pId);

            //TODO 后续加了product相关的表，这里需要相应删除
            tran.Commit();
        }
        catch
        {
            tran.Rollback();
        }
        finally
        {
            conn.Close();
        }

        BindData();
    }
}
