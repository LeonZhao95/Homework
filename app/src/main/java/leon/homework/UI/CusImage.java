
package leon.homework.UI;

/**
 * @author Vyshakh, Rahul
 *
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class CusImage extends View {

	private Paint myPaint;
	private Paint myFramePaint;
	public TextView value;
	private float startAngle;
	public float temp;
	float sweepAngle;
	private int flag = 0;
	RectF rect;
	private MasterLayout m;
	int pix = 0;
    Boolean isfirst = true;
	public CusImage(Context context, AttributeSet attrs, MasterLayout m) {
		super(context, attrs);
		this.m = m;
	}

	public CusImage(Context context, MasterLayout m) {
		super(context);
		this.m = m;
	}

	private void init() {

		myPaint = new Paint();
		myPaint.setAntiAlias(true);
		myPaint.setStyle(Paint.Style.STROKE);
		myPaint.setColor(Color.rgb(0, 161, 234));  //Edit this to change progress arc color.
		myPaint.setStrokeWidth((float) (pix*0.1));

		myFramePaint = new Paint();
		myFramePaint.setAntiAlias(true);
		myFramePaint.setColor(Color.TRANSPARENT);

		float startx = (float) (pix * 0.1);
		float endx = (float) (pix * 0.9);
		float starty = (float) (pix * 0.1);
		float endy = (float) (pix * 0.9);
		rect = new RectF(startx, starty, endx, endy);
	}

	public void setupprogress(int progress) {

		//Updating progress arc 
		
		sweepAngle = (float) (progress * 3.6);
		
	}

	public void reset() {

		//Resetting progress arc
		
		sweepAngle = 0;
		startAngle = -90;
		flag = 1;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		pix = MeasureSpec.getSize(widthMeasureSpec);
		if(isfirst){
			init();
			isfirst=false;
		}
	}
	/*@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int desiredWidth = pix;
		int desiredHeight = pix;
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width;
		int height;

		
		if (widthMode == MeasureSpec.EXACTLY) {
			
			width = widthSize;
		} else if (widthMode == MeasureSpec.AT_MOST) {
			
			width = Math.min(desiredWidth, widthSize);
		} else {
			
			width = desiredWidth;
		}

		
		if (heightMode == MeasureSpec.EXACTLY) {
			
			height = heightSize;
		} else if (heightMode == MeasureSpec.AT_MOST) {
			
			height = Math.min(desiredHeight, heightSize);
		} else {
			
			height = desiredHeight;
		}

		
		setMeasuredDimension(width, height);
	}*/

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawArc(rect, startAngle, sweepAngle, false, myPaint);
		startAngle = -90;

		if (sweepAngle < 360 && flag == 0) {

			invalidate();

		} else if (flag == 1) {

			sweepAngle = 0;
			startAngle = -90;
			flag = 0;
			invalidate();
		} else {

			sweepAngle = 0;
			startAngle = -90;
			m.finalAnimation();

		}
	}
}
