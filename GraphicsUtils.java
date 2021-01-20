package com.puer.brand.common.util;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 海报 画笔绘制工具类
 *
 * @author Angzk
 * @date 2019年8月7日
 */
public class GraphicsUtils {

    private static Font msyhFont;

    private static Font msyhBoldFont;

    /**
     * @param param 代码中的 坐标 依据UI 切图的 像素值。 请根据个人需要调整
     */
    public static InputStream createPosterByRedTemplate(ImageParam param) {
        String backgroundUrl = param.getBackgroundUrl();
        String constant = param.getConstant();
        String is = param.getHeadPhoto();
        String nickName = param.getNickName();
        String spuPicUrl = param.getSpuPicUrl();
        String price = param.getPrice();
        String goodsName = param.getGoodsName();
        QrParam qrParam = param.getQrParam();
        Color firstColor = param.getFirstColor();
        String qrPath = qrParam.getQrPath();
        int x = qrParam.getX();
        int y = qrParam.getY();

        // 海报背景
        BufferedImage bufferImage = QrCodeBaseUtils.imageToBufferedImage(backgroundUrl);
        if (bufferImage != null) {

            Graphics2D graphics = bufferImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graphics = bufferImage.createGraphics();
            //小程序码图
            BufferedImage qrCodeImage = QrCodeBaseUtils.imageToBufferedImage(qrPath);

            // 绘制 qrCode
            graphics.drawImage(qrCodeImage, x, y, 140, 140, null);

            // 绘制 头像
            graphics = QrCodeGraphicsUtils.drawAvatar(graphics, is, bufferImage, 13, 10);

            // 商品主图
            BufferedImage spuPicBufferImage = QrCodeBaseUtils.imageToBufferedImage(spuPicUrl);
            if (spuPicBufferImage != null) {
                // 绘制商品主图
                graphics.drawImage(spuPicBufferImage, 35, 112, 339, 310, null);
            }
            if (goodsName != null) {
                // 文本
                QrCodeGraphicsUtils.drawTextNewLine(graphics, goodsName, 33, 570, 20, 170, Color.BLACK, 13, 4, 60);
            }


            // 第一个文本
//            Font font = new Font("微软雅黑", Font.BOLD, 13);
            graphics.setFont(msyhBoldFont);
            QrCodeGraphicsUtils.drawText(graphics, nickName, 93, 38, firstColor);
            // 第二个文本
//            Font font2 = new Font("微软雅黑", Font.BOLD, 13);
            graphics.setFont(msyhBoldFont);
            QrCodeGraphicsUtils.drawText(graphics, constant, 93, 65, Color.WHITE);

            if (price != null) {
                // 价格
//                Font font3 = new Font("微软雅黑", Font.PLAIN, 16);

                graphics.setFont(msyhFont);
                QrCodeGraphicsUtils.drawText(graphics, price, 33, 450, new Color(222, 96, 58));
            }

            graphics.dispose();

            ByteArrayOutputStream os = null;
            InputStream input = null;
            try {
                os = new ByteArrayOutputStream();
                BufferedImage image = Thumbnails.of(bufferImage).scale(1.0).asBufferedImage();
                ImageIO.write(image, "JPG", os);
                input = new ByteArrayInputStream(os.toByteArray());
                return input;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                CloseStreamUtil.close(os, input, "生成海报关流异常");
            }
        }
        return null;
    }

    /**
     * getResource 返回的 Path 可能会以 / 开头,这里做了截取.
     *
     * @param path 图片地址
     * @return String
     */
    public static String handlePath(String path) {
        if (StringUtils.isNotBlank(path)) {
            if (path.startsWith("/")) {
                System.err.println("true");
                path = path.substring(1);
            }
        }
        return path;
    }

    static {
        InputStream inputStream = null;
        InputStream boldInputStream = null;
        try {
            inputStream = GraphicsUtils.class.getClassLoader().getResourceAsStream("font/msyh.ttc");
            boldInputStream = GraphicsUtils.class.getClassLoader().getResourceAsStream("font/msyhbd.ttc");
            if (inputStream != null && boldInputStream != null) {
                msyhFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
                msyhFont = msyhFont.deriveFont(Font.PLAIN,16);
                msyhBoldFont = Font.createFont(Font.TRUETYPE_FONT, boldInputStream);
                msyhBoldFont = msyhBoldFont.deriveFont(Font.BOLD,13);
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        } finally {
            CloseStreamUtil.close(boldInputStream,inputStream,"字体异常");
        }

    }
}
