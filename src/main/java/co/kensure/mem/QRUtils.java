package co.kensure.mem;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import co.kensure.io.FileUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码工具类
 * 
 * @author fan.kaidi
 *
 */
public class QRUtils {

	private final static String format = "png";

	/**
	 * 生成二维码
	 * 
	 * @return
	 */
	public static void genQR(int width, int height, String url, String path) {
		Map<EncodeHintType, Object> map = new HashMap<EncodeHintType, Object>();
		map.put(EncodeHintType.CHARACTER_SET, "utf-8");
		map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		map.put(EncodeHintType.MARGIN, 0);
		FileUtils.createDir(path);
		try {
			BitMatrix bm = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height);
			Path file = new File(path).toPath();		
			MatrixToImageWriter.writeToPath(bm, format, file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		String weixin_appid = "wx10500396829a6d44";
    	String REDIRECT_URI = "http://www.52shibei.com/shike/skm/login2";
    	String  codeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+weixin_appid+"&redirect_uri="+REDIRECT_URI+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		genQR(300, 300, codeUrl, "D:/img.png");
	}

}
