using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MySql.Data.MySqlClient;
using System.Configuration;
using System.Data;
using System.Text;

public partial class ProductAdd : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        Form.Enctype = "multipart/form-data";
    }

    protected void btnAdd_Click(object sender, EventArgs e)
    {
        string theProductId = "";
        string strSql = "insert into product_info" +
            " (name,market_price,my_price,type_id,description,sizes,view,gmt_created,gmt_modified,attributes,cover_img_name)" +
            " values(@name,@market_price,@my_price,@type_id,@description,@sizes,0,now(),now(),@attributes,@cover_img_name);SELECT @@IDENTITY as id;";
        List<MySqlParameter> pList = new List<MySqlParameter>();
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


        //TODO 产品封面图片，新加，可能有ｂｕｇ
        if (fuCoverImage.HasFile)
        {
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
        else
        {
            pList.Add(new MySqlParameter("@cover_img_name", null));
        }


        MySqlConnection conn = new MySqlConnection(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
        conn.Open();
        MySqlTransaction tran = conn.BeginTransaction();
        try
        {
            long productId = (long)MySqlHelper.ExecuteScalar(conn, strSql, pList.ToArray());
            theProductId = productId.ToString();

            strSql = "insert into product_washing(product_id,washing_id,gmt_created,gmt_modified)" +
                " values(@product_id,@washing_id,now(),now());";
            foreach (ListItem washItem in cblWashing.Items)
            {
                if (!washItem.Selected)
                    continue;
                MySqlHelper.ExecuteNonQuery(conn, strSql, new MySqlParameter("@product_id", productId),
                    new MySqlParameter("@washing_id", washItem.Value));
            }

            string[] colorNames = Request["colorName"].Split(',');
            HttpFileCollection files = Request.Files;

            strSql = "insert into product_color(product_id,color_name,img_name,gmt_created,gmt_modified)" +
              " values(@product_id,@color_name,@img_name,now(),now())";
            for (int iColor = -1, iFile = 0; iFile < files.Count - 1; iFile++)
            {
                if (files.AllKeys[iFile] != "colorFile")
                    continue;
                else
                    iColor++;
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

            tran.Commit();
        }
        catch (Exception ex)
        {
            System.IO.File.AppendAllText("/tmp/aa.log", ex.Message);
            System.IO.File.AppendAllText("/tmp/aa.log", ex.Source);
            tran.Rollback();
        }
        finally
        {
            conn.Close();
        }
        Server.Transfer("ColorImgAdd.aspx?id=" + theProductId);
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
        if (ds.Tables[0].Rows.Count == 0) ddlCatagoryG2.Visible = false;
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
            ddlCatagoryG1.DataBind();
            BindCategoryG2();
        }
    }

}
