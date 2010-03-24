using System;
using MySql.Data.MySqlClient;
using System.Configuration;
using System.Collections.Generic;

/// <summary>
///CommonHelper 的摘要说明
/// </summary>
public static class CommonHelper
{
    public static string UploadPathUrl = "/images/uploads/";
    public static string UploadFilePath
    {
        get
        {
            switch (Environment.OSVersion.Platform)
            {
                case PlatformID.Win32NT:
                    return System.Web.HttpContext.Current.Server.MapPath("~/images/uploads/");
                //case PlatformID.Unix:
                default:
                    return "/home/exhk/deploy/web/exhk/images/uploads/";

            }
        }
    }

    public static string DeleteFilePath
    {
        get
        {
            switch (Environment.OSVersion.Platform)
            {
                case PlatformID.Win32NT:
                    return System.Web.HttpContext.Current.Server.MapPath("~/images/uploads/");
                //case PlatformID.Unix:
                default:
                    return "/home/exhk/deploy/web/exhk/images/deleted/";
            }
        }
    }

    private static Dictionary<string, string> _orderEnum;

    public static string GetOrderNameByID(string ID)
    {
        string name;
        if (_orderEnum.TryGetValue(ID, out name)) return name;
        else return null;
    }

    public static Dictionary<string, string> GetAllOrderIDandName()
    {
        return _orderEnum;
    }

    static CommonHelper()
    {
        _orderEnum = new Dictionary<string, string>(7);
        _orderEnum.Add("INITIALIZED", "已下单");
        _orderEnum.Add("DELETED", "取消");
        _orderEnum.Add("PAID", "已付款");
        _orderEnum.Add("DELIVERED", "已发货");
        _orderEnum.Add("NOT_ARRIVED", "无法送达");
        _orderEnum.Add("SUCCESS", "成功");
        _orderEnum.Add("FAIL", "失败");
    }

    /// <summary> 生成缩略图 
    /// 
    /// </summary> 
    /// <param name="originalImagePath">源图路径（物理路径）</param> 
    /// <param name="thumbnailPath">缩略图路径（物理路径）</param> 
    /// <param name="width">缩略图宽度</param> 
    /// <param name="height">缩略图高度</param> 
    /// <param name="mode">生成缩略图的方式</param> 
    /// <param name="mode">FIX,HW,W,H,Cut</param> 
    public static void MakeThumbnail(string originalImagePath, string thumbnailPath, int width, int height, string mode)
    {
        System.Drawing.Image originalImage = System.Drawing.Image.FromFile(originalImagePath);

        int towidth = width;
        int toheight = height;

        int x = 0;
        int y = 0;
        int ow = originalImage.Width;
        int oh = originalImage.Height;

        switch (mode)
        {
            case "FIX"://按比例自动缩放
                if (towidth == ow && toheight == oh)
                {
                    System.IO.File.Copy(originalImagePath, thumbnailPath);
                }
                else if (ow >= towidth || oh >= toheight)//缩小到最大
                {
                    if ((double)ow / oh >= (double)towidth / toheight) //文件宽高比大于目标,即原文件图片太胖,宽度按照目标,高度将小于目标尺寸
                    {
                        toheight = towidth * oh / ow;
                    }
                    else
                    {
                        towidth = toheight * ow / oh;
                    }
                }
                else//放大到最小
                {
                    if ((double)ow / oh >= (double)towidth / toheight) //文件宽高比大于目标,即原文件图片太胖,宽度按照目标,高度将小于目标尺寸
                    {
                        toheight = towidth * oh / ow;
                    }
                    else
                    {
                        towidth = toheight * ow / oh;
                    }

                }
                break;
            case "HW"://指定高宽缩放（可能变形） 
                break;
            case "W"://指定宽，高按比例 
                toheight = originalImage.Height * width / originalImage.Width;
                break;
            case "H"://指定高，宽按比例 
                towidth = originalImage.Width * height / originalImage.Height;
                break;
            case "Cut"://指定高宽裁减（不变形） 
                if ((double)originalImage.Width / (double)originalImage.Height > (double)towidth / (double)toheight)
                {
                    oh = originalImage.Height;
                    ow = originalImage.Height * towidth / toheight;
                    y = 0;
                    x = (originalImage.Width - ow) / 2;
                }
                else
                {
                    ow = originalImage.Width;
                    oh = originalImage.Width * height / towidth;
                    x = 0;
                    y = (originalImage.Height - oh) / 2;
                }
                break;
            default:
                break;
        }

        //新建一个bmp图片 
        System.Drawing.Image bitmap = new System.Drawing.Bitmap(towidth, toheight);

        //新建一个画板 
        System.Drawing.Graphics g = System.Drawing.Graphics.FromImage(bitmap);

        //设置高质量插值法 
        g.InterpolationMode = System.Drawing.Drawing2D.InterpolationMode.High;

        //设置高质量,低速度呈现平滑程度 
        g.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.HighQuality;

        //清空画布并以透明背景色填充 
        g.Clear(System.Drawing.Color.Transparent);

        //在指定位置并且按指定大小绘制原图片的指定部分 
        g.DrawImage(originalImage, new System.Drawing.Rectangle(0, 0, towidth, toheight),
        new System.Drawing.Rectangle(x, y, ow, oh),
        System.Drawing.GraphicsUnit.Pixel);

        try
        {
            //以jpg格式保存缩略图 
            bitmap.Save(thumbnailPath, System.Drawing.Imaging.ImageFormat.Jpeg);
        }
        catch (System.Exception e)
        {
            throw e;
        }
        finally
        {
            originalImage.Dispose();
            bitmap.Dispose();
            g.Dispose();
        }
    }


    /// <summary>CheckRequestId
    /// 检查url中传入参数id的值的正确性
    /// 正确情况返回id个数，应为1
    /// 错误情况返回-1
    /// 错误情况有：id不可解析为数字，id不在该表中
    /// </summary>
    public static long CheckRequestId(string requestID, string tableName, string otherWhere)
    {
        long id = -1;
        if (requestID == null || requestID == "" || !long.TryParse(requestID, out id))
        {
            return -1;
        }
        long count = (long)MySqlHelper.ExecuteScalar(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
                "select count(id) from " + tableName + " where id=" + id.ToString() + (otherWhere != null ? " and " + otherWhere : ""));
        if (count == 0) return -1;
        return id;
    }

    public static long CheckRequestId(string requestID, string tableName)
    {
        return CheckRequestId(requestID, tableName, null);
    }


    public static void DeleteImgFile(string fileName)
    {
        if (System.IO.File.Exists(CommonHelper.UploadFilePath + fileName))
            System.IO.File.Move(CommonHelper.UploadFilePath + fileName, CommonHelper.DeleteFilePath + fileName);
        if (System.IO.File.Exists(CommonHelper.UploadFilePath + "t_" + fileName))
            System.IO.File.Move(CommonHelper.UploadFilePath + "t_" + fileName,
                CommonHelper.DeleteFilePath + "t_" + fileName);

    }

}


