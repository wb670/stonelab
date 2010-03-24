using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using MySql.Data.MySqlClient;
using System.Configuration;
using System.Web.UI.HtmlControls;

public partial class ColorImgAdd : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        Form.Enctype = "multipart/form-data";

        if (!IsPostBack)
        {
            if (Request["id"] == null || Request["id"] == "" || !BindData(Request["id"]))
            {
                Response.Redirect("ProductList.aspx");
            }
        }
    }

    private bool BindData(string id)
    {
        string strSql = "select type_id,id,name from product_info where id=@id limit 1";
        DataRow dr = MySqlHelper.ExecuteDataRow(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                strSql, new MySqlParameter("@id", id));
        if (dr == null)
            return false;

        labId.Text = dr["id"].ToString();
        labName.Text = dr["name"].ToString();
        return true;
    }

    protected void Repeater1_ItemCommand(object source, RepeaterCommandEventArgs e)
    {
        if (e.CommandName == "add")
        {
            string colorId = e.CommandArgument.ToString();

            //有文件名继续，没文件名就是被耍了
            if (Request.Files["file" + colorId].FileName == "")
            {
                ClientScript.RegisterStartupScript(Page.GetType(), "warning", "<script>您忘记选择文件了！请先选择文件，再点击添加</script>");
                return;
            }
            string strSql = "select img_group_id from product_color where id=@id";
            object oResult = MySqlHelper.ExecuteScalar(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                strSql, new MySqlParameter("@id", colorId));

            long imgGroupId;
            ///添加图片，
            ///当img_group_id为null时，1插group,2上传图片，3插img，4更新color
            ///否则，上传图片，插img
            if (oResult.GetType().Name == "DBNull")
            {
                strSql = "insert into img_group(name,gmt_created,gmt_modified) values(@name,now(),now());SELECT @@IDENTITY as id";
                imgGroupId = (long)MySqlHelper.ExecuteScalar(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                    strSql, new MySqlParameter("@name", "ProductId" + Request["id"] + "_ColorId" + colorId));
            }
            else
            {
                imgGroupId = (long)(uint)oResult;
            }


            HttpPostedFile postedFile = Request.Files["file" + colorId];
            string fileExtension;
            fileExtension = System.IO.Path.GetExtension(postedFile.FileName);

            string newFileName = Request["id"] + "_" + colorId + DateTime.Now.ToString("yyyyMMddHHmmss") + fileExtension;
            postedFile.SaveAs(CommonHelper.UploadFilePath + "temp" + newFileName);

            CommonHelper.MakeThumbnail(CommonHelper.UploadFilePath + "temp" + newFileName,
                CommonHelper.UploadFilePath + newFileName, 1024, 0, "W");
            CommonHelper.MakeThumbnail(CommonHelper.UploadFilePath  + "temp"+ newFileName,
                CommonHelper.UploadFilePath + "t_" + newFileName, 300, 100, "W");

            System.IO.File.Delete(CommonHelper.UploadFilePath + "temp" + newFileName);
            
            strSql = "insert into img(group_id,img_name,order_id,gmt_created,gmt_modified) " +
                " values(@group_id,@img_name,@order_id,now(),now())";
            MySqlHelper.ExecuteNonQuery(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                strSql, new MySqlParameter("@group_id", imgGroupId),
                new MySqlParameter("@img_name", newFileName),
                new MySqlParameter("@order_id", 100));

            if (oResult.GetType().Name == "DBNull")
            {
                strSql = "update product_color set img_group_id=@img_group_id where id=@id";
                MySqlHelper.ExecuteNonQuery(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                    strSql, new MySqlParameter("@img_group_id", imgGroupId),
                    new MySqlParameter("@id", colorId));
            }
            Repeater1.DataBind();
        }
    }


    protected void Repeater2_ItemCommand(object source, RepeaterCommandEventArgs e)
    {
        if (e.CommandName == "del")
        {
            string[] args = e.CommandArgument.ToString().Split('|');

            CommonHelper.DeleteImgFile(args[1]);

            string strSql = "delete from img where id=@id";
            MySqlHelper.ExecuteNonQuery(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                strSql, new MySqlParameter("@id", args[0]));

            Repeater1.DataBind();
        }
    }

    protected void Repeater1_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
        {
            string imgGroupId = ((HiddenField)e.Item.FindControl("HiddenFieldImgGroupId")).Value;
            if (imgGroupId != "")
            {
                string strSql = "SELECT  img_name,id as img_id FROM img where group_id = @id order by order_id";
                DataSet ds = MySqlHelper.ExecuteDataset(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                strSql, new MySqlParameter("@id", imgGroupId));
                Repeater rpt = (Repeater)e.Item.FindControl("Repeater2");
                rpt.DataSource = ds;
                rpt.DataBind();
            }
            else
            {
                ((Repeater)e.Item.FindControl("Repeater2")).DataSource = null;
            }

        }

    }
}

