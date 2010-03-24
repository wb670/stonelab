using System;
using System.Collections;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using MySql.Data.MySqlClient;
using System.Configuration;
using System.Text;

public partial class ProductEdit : System.Web.UI.Page
{
    protected void Page_Init(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            if (CommonHelper.CheckRequestId(Request["id"], "product_info") == -1)
                Response.Redirect("ProductList.aspx");
        }
    }


    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            Form.Enctype = "multipart/form-data";
            ViewState["id"] = Request["id"];
        }
    }

    private void BindData()
    {
        string strSql = "select type_id, name, description, market_price, my_price, attributes, sizes, view, cover_img_name from product_info where id= @id ";
        DataRow dr = MySqlHelper.ExecuteDataRow(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
            strSql, new MySqlParameter("@id", (string)ViewState["id"]));

        txtName.Text = dr["name"].ToString();
        txtMarketPrice.Text = dr["market_price"].ToString();
        txtMyPrice.Text = dr["my_price"].ToString();
        txtDescription.Text = dr["description"].ToString();
        if (dr["cover_img_name"].ToString() != "")
        {
            imgCoverImage.Visible = true;
            imgCoverImage.ImageUrl = CommonHelper.UploadPathUrl + "t_" + dr["cover_img_name"].ToString();
        }
        else
            imgCoverImage.Visible = false;

        cblSize.DataBind();
        if (dr["sizes"].ToString() != "")
        {
            foreach (string strSize in dr["sizes"].ToString().Split(','))
            {
                ListItem item = cblSize.Items.FindByValue(strSize);
                if (item != null) item.Selected = true;
            }
        }

        MySqlDataReader reader = MySqlHelper.ExecuteReader(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
            "select washing_id from product_washing where product_id=@id", new MySqlParameter("@id", (string)ViewState["id"]));
        cblWashing.DataBind();
        while (reader.Read())
        {
            cblWashing.Items.FindByValue(reader["washing_id"].ToString()).Selected = true;
        }
        reader.Close();

        if (dr["attributes"].ToString() != "")
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("name");
            dt.Columns.Add("value");
            foreach (string snv in dr["attributes"].ToString().Split(','))
            {
                string[] nv = snv.Split('=');
                dt.Rows.Add(nv[0], nv[1]);
            }
            RepeaterAtt.DataSource = dt;
            RepeaterAtt.DataBind();
        }

        ddlCatagoryG1.DataBind();
        ListItem li = ddlCatagoryG1.Items.FindByValue(dr["type_id"].ToString());
        if (li != null)
        {
            li.Selected = true;
            BindCategoryG2();
        }
        else
        {
            strSql = "select depth,parent_id from category where id= @id ";
            DataRow typeRow = MySqlHelper.ExecuteDataRow(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
            strSql, new MySqlParameter("@id", dr["type_id"].ToString()));
            ddlCatagoryG1.SelectedValue = typeRow["parent_id"].ToString();

            BindCategoryG2();
            ddlCatagoryG2.SelectedValue = dr["type_id"].ToString();
        }
    }

    protected void ddlCatagoryG1_SelectedIndexChanged(object sender, EventArgs e)
    {
        BindCategoryG2();
    }

    private void BindCategoryG2()
    {
        DataSet ds = MySqlHelper.ExecuteDataset(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
            "select id,name from category where parent_id=@parent_id",
            new MySqlParameter("@parent_id", ddlCatagoryG1.SelectedValue));
        if (ds.Tables[0].Rows.Count == 0)
        {
            ddlCatagoryG2.DataSource = null;
            ddlCatagoryG2.DataBind();
            ddlCatagoryG2.Visible = false;
        }
        else
        {
            ddlCatagoryG2.Visible = true;
            ddlCatagoryG2.DataSource = ds;
            ddlCatagoryG2.DataBind();
        }
    }

    protected void Page_PreRender(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            BindData();
        }
    }

    protected void RepeaterColor_ItemCommand(object source, RepeaterCommandEventArgs e)
    {
        if (e.CommandName == "del")
        {
            string[] args = e.CommandArgument.ToString().Split('|');

            MySqlParameter pId = new MySqlParameter("@id", args[0]);
            CommonHelper.DeleteImgFile(args[1]);

            MySqlConnection conn = new MySqlConnection(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
            conn.Open();
            MySqlTransaction tran = conn.BeginTransaction();

            try
            {
                string strSql = "select img_name,img_group_id from product_color where product_id=@id";
                MySqlDataReader reader = MySqlHelper.ExecuteReader(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                    strSql, pId);
                while (reader.Read())
                {
                    CommonHelper.DeleteImgFile(reader["img_name"].ToString());

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
                reader.Close();

                strSql = "delete from product_color where id=@id";
                MySqlHelper.ExecuteNonQuery(conn, strSql, pId);

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
            RepeaterColor.DataBind();
        }
    }

    protected void btnModify_Click(object sender, EventArgs e)
    {
        string theProductId = "";

        string strSql = "update product_info" +
                  " set name=@name, market_price=@market_price,my_price=@my_price,type_id=@type_id,description=@description," +
                  "sizes=@sizes,attributes=@attributes,gmt_modified=now(),cover_img_name=@cover_img_name where id=@id";
        List<MySqlParameter> pList = new List<MySqlParameter>();
        pList.Add(new MySqlParameter("@id", (string)ViewState["id"]));
        pList.Add(new MySqlParameter("@name", txtName.Text));
        pList.Add(new MySqlParameter("@market_price", txtMarketPrice.Text));
        pList.Add(new MySqlParameter("@my_price", txtMyPrice.Text));
        pList.Add(new MySqlParameter("@description", txtDescription.Text));
        if (ddlCatagoryG2.Visible == true)
            pList.Add(new MySqlParameter("@type_id", ddlCatagoryG2.SelectedValue));
        else
            pList.Add(new MySqlParameter("@type_id", ddlCatagoryG1.SelectedValue));

        StringBuilder sb = new StringBuilder();
        foreach (ListItem li in cblSize.Items)
        {
            if (li.Selected) sb.Append(li.Value).Append(',');
        }
        if (sb.Length > 0)
            pList.Add(new MySqlParameter("@sizes", sb.Remove(sb.Length - 1, 1).ToString()));
        else
            pList.Add(new MySqlParameter("@sizes", null));

        string[] aks = Request["ak"].Split(',');
        string[] avs = Request["av"].Split(',');
        sb.Remove(0, sb.Length);
        for (int i = 0; i < aks.Length - 1; i++)
        {
            if (aks[i] == "")
                continue;
            sb.Append(aks[i]).Append('=').Append(avs[i]).Append(',');
        }
        if (sb.Length > 0)
            pList.Add(new MySqlParameter("@attributes", sb.Remove(sb.Length - 1, 1).ToString()));
        else
            pList.Add(new MySqlParameter("@attributes", null));

        if (fuCoverImage.HasFile)
        {
            if (imgCoverImage.ImageUrl != "")
            {
                CommonHelper.DeleteImgFile(imgCoverImage.ImageUrl);
            }
            string fileName = System.IO.Path.GetFileNameWithoutExtension(fuCoverImage.FileName);
            string fileExtension = System.IO.Path.GetExtension(fuCoverImage.FileName);

            string newFileName = fileName + DateTime.Now.ToString("yyyyMMddHHmmss") + fileExtension;
            fuCoverImage.SaveAs(CommonHelper.UploadFilePath + "temp" + newFileName);

            CommonHelper.MakeThumbnail(CommonHelper.UploadFilePath + "temp" + newFileName,
                CommonHelper.UploadFilePath + newFileName, 1024, 0, "W");
            CommonHelper.MakeThumbnail(CommonHelper.UploadFilePath + "temp" + newFileName,
                CommonHelper.UploadFilePath + "t_" + newFileName, 300, 100, "W");

            System.IO.File.Delete(CommonHelper.UploadFilePath + "temp" + newFileName);

            pList.Add(new MySqlParameter("@cover_img_name", newFileName));
        }
        else if (imgCoverImage.ImageUrl != "")
        {
            pList.Add(new MySqlParameter("@cover_img_name", System.IO.Path.GetFileName(imgCoverImage.ImageUrl).Remove(0, 2)));
        }
        else
        {
            pList.Add(new MySqlParameter("@cover_img_name", null));
        }


        MySqlConnection conn = new MySqlConnection(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
        conn.Open();
        MySqlTransaction tran = conn.BeginTransaction();

        try
        {

            MySqlHelper.ExecuteNonQuery(conn, strSql, pList.ToArray());
            long productId = long.Parse((string)ViewState["id"]);
            theProductId = productId.ToString();

            strSql = "delete from product_washing where product_id=@product_id";
            MySqlHelper.ExecuteNonQuery(conn, strSql, new MySqlParameter("@product_id", productId));

            strSql = "insert into product_washing(product_id,washing_id,gmt_created,gmt_modified)" +
                " values(@product_id,@washing_id,now(),now())";
            foreach (ListItem washItem in cblWashing.Items)
            {
                if (!washItem.Selected)
                    continue;
                MySqlHelper.ExecuteNonQuery(conn, strSql, new MySqlParameter("@product_id", productId),
                    new MySqlParameter("@washing_id", washItem.Value));
            }

            //新上传的颜色及色卡
            string[] colorNames = Request["colorName"].Split(',');
            HttpFileCollection files = Request.Files;

            strSql = "insert into product_color(product_id,color_name,img_name,gmt_created,gmt_modified)" +
              " values(@product_id,@color_name,@img_name,now(),now())";
            for (int iFile = 0, iColor = -1; iFile < files.Count - 1; iFile++)
            {
                if (!files.AllKeys[iFile].StartsWith("colorFile"))
                {
                    continue;
                }
                else if (files.AllKeys[iFile] != "colorFile")
                {
                    iColor++;
                    continue;
                }
                else
                {
                    iColor++;
                }

                HttpPostedFile postedFile = files[iFile];
                string fileName, fileExtension;
                fileName = System.IO.Path.GetFileName(postedFile.FileName);
                if (colorNames[iColor] != "" && fileName != "")
                {
                    fileExtension = System.IO.Path.GetExtension(fileName);

                    string newFileName = productId + "_" + colorNames[iColor] + DateTime.Now.ToString("yyyyMMddHHmmss") + fileExtension;
                    postedFile.SaveAs(CommonHelper.UploadFilePath + "temp" + newFileName);

                    CommonHelper.MakeThumbnail(CommonHelper.UploadFilePath + "temp" + newFileName,
                        CommonHelper.UploadFilePath + newFileName, 1024, 0, "W");
                    CommonHelper.MakeThumbnail(CommonHelper.UploadFilePath + "temp" + newFileName,
                        CommonHelper.UploadFilePath + "t_" + newFileName, 300, 100, "W");

                    System.IO.File.Delete(CommonHelper.UploadFilePath + "temp" + newFileName);

                    MySqlHelper.ExecuteNonQuery(conn, strSql, new MySqlParameter("@product_id", productId),
                        new MySqlParameter("@color_name", colorNames[iColor]),
                        new MySqlParameter("@img_name", newFileName));
                }
            }

            foreach (RepeaterItem ri in RepeaterColor.Items)
            {
                string id = ((HiddenField)ri.FindControl("HiddenFieldId")).Value;
                string colorName = id.Split('|')[1];
                string oldImgName = id.Split('|')[2];
                id = id.Split('|')[0];

                if (Request.Files["colorFile" + id].FileName != "")
                {
                    string fileExtension = System.IO.Path.GetExtension(Request.Files["colorFile" + id].FileName);

                    string newFileName = productId + "_" + colorName + DateTime.Now.ToString("yyyyMMddHHmmss") + fileExtension;
                    Request.Files["colorFile" + id].SaveAs(CommonHelper.UploadFilePath + "temp" + newFileName);

                    CommonHelper.MakeThumbnail(CommonHelper.UploadFilePath + "temp" + newFileName,
                        CommonHelper.UploadFilePath + newFileName, 1024, 0, "W");
                    CommonHelper.MakeThumbnail(CommonHelper.UploadFilePath + "temp" + newFileName,
                        CommonHelper.UploadFilePath + "t_" + newFileName, 300, 100, "W");

                    System.IO.File.Delete(CommonHelper.UploadFilePath + "temp" + newFileName);

                    strSql = "update product_color set img_name=@img_name,gmt_modified=now() where id=@id ";
                    MySqlHelper.ExecuteNonQuery(conn, strSql, new MySqlParameter("@id", id),
                        new MySqlParameter("@img_name", newFileName));

                    CommonHelper.DeleteImgFile(oldImgName);
                }
            }

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
        Server.Transfer("ColorImgAdd.aspx?id=" + theProductId);
    }
}
