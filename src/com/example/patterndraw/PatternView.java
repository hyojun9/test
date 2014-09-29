package com.example.patterndraw;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

public class PatternView extends View
{
	final int POS_LINE_COLOR = Color.RED;
	final int POS_FILL_COLOR = Color.parseColor("#55ff0000");
	final int COMMON_LINE_COLOR = Color.BLACK;
	final int NEG_LINE_COLOR = Color.BLUE;
	final int NEG_FILL_COLOR = Color.parseColor("#550000ff");

	public PatternView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		//my test
		m_lineBrush.setColor(POS_LINE_COLOR);
		m_lineBrush.setStrokeWidth(1);
		m_lineBrush.setStyle(Paint.Style.STROKE);
		m_lineBrush.setAntiAlias(true);

		m_fillBrush.setColor(POS_FILL_COLOR);
		m_fillBrush.setStrokeWidth(0);
		m_fillBrush.setStyle(Paint.Style.FILL);
		m_fillBrush.setAntiAlias(true);

		m_dashBrush.setColor(POS_LINE_COLOR);
		m_dashBrush.setStrokeWidth(1);
		m_dashBrush.setAntiAlias(true);
		m_dashBrush.setStyle(Paint.Style.STROKE);
		m_dashBrush.setPathEffect(new DashPathEffect(new float[]
		{ 5, 5 }, 0));

		m_labelBrush.setTextAlign(Align.CENTER);
		m_labelBrush.setAntiAlias(true);

		// 샘플 poinsts
		samplePoints.add(new PointF(30, 520));
		samplePoints.add(new PointF(154, 220));
		samplePoints.add(new PointF(219, 368));
		samplePoints.add(new PointF(353, 63));
		samplePoints.add(new PointF(469, 367));
		samplePoints.add(new PointF(532, 217));
		samplePoints.add(new PointF(587, 368));
	}

	ArrayList<PointF> samplePoints = new ArrayList<PointF>();

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		// DrawFlag(samplePoints, new PointF(259, 30), "10,000" , canvas , true);
		// DrawFlag(samplePoints, new PointF(328, 429), "10,000", canvas , false);
		// DrawPennant(samplePoints, new PointF(373, 7), "10,000", canvas, true);
		// DrawPennant(samplePoints, new PointF(400, 518), "10,000", canvas, false);
		// DrawRectangle(samplePoints, new PointF(377, 34), "10,000", canvas, true);
		//DrawRectangle(samplePoints, new PointF(360, 523), "10,000", canvas, false);
		//DrawTriangleSymmentrical(samplePoints, new PointF(599, 57), "10,000", canvas, true);
		//DrawTriangleAsc(samplePoints, new PointF(499, 602), "10,000", canvas);
		//DrawTriangleDes(samplePoints, new PointF(499, 602), "10,000", canvas);
		//DrawWedge(samplePoints, new PointF(530, 68), "10,000", canvas , true);
		//DrawWedge(samplePoints, new PointF(616, 616), "10,000", canvas , false);
		DrawHeadShoulders(samplePoints, new PointF(704, 675), "10,000", canvas , false);
	}

	protected Path m_pathTool = new Path();
	Paint m_lineBrush = new Paint();
	Paint m_dashBrush = new Paint();
	Paint m_fillBrush = new Paint();
	Paint m_labelBrush = new Paint();
	final int LABEL_TEXT_SIZE = 12;

	protected boolean CheckValid(ArrayList<PointF> pointArray, int validPointNum)
	{
		// point 오류 검사
		if (pointArray == null || pointArray.size() < validPointNum)
		{
			return false;
		}
		return true;

	}

	public void DrawFlag(ArrayList<PointF> pointArray, PointF pointT, String targetVal, Canvas canvas, boolean isAsc)
	{
		// 공통 체크 함수
		if (CheckValid(pointArray, 6) == false)
		{
			return;
		}

		// 방향에 따른 옵션 조정
		int labelGravity = Gravity.BOTTOM;
		int targetLabelGravity = Gravity.TOP;
		int lineColor = POS_LINE_COLOR;
		int fillColor = POS_FILL_COLOR;
		String labelName = "";
		if (isAsc == true)
		{
			labelGravity = Gravity.BOTTOM;
			targetLabelGravity = Gravity.TOP;
			lineColor = POS_LINE_COLOR;
			fillColor = POS_FILL_COLOR;
			labelName = "상승 깃대형";
		}
		else
		{
			labelGravity = Gravity.TOP;
			targetLabelGravity = Gravity.BOTTOM;
			lineColor = NEG_LINE_COLOR;
			fillColor = NEG_FILL_COLOR;
			labelName = "하락 깃대형";
		}
		m_lineBrush.setColor(lineColor);
		m_fillBrush.setColor(fillColor);
		m_dashBrush.setColor(lineColor);

		// 그릴 포인트 준비
		PointF point0 = pointArray.get(0);
		PointF point1 = pointArray.get(1);
		PointF point2 = pointArray.get(2);
		PointF point3 = pointArray.get(3);
		PointF point4 = pointArray.get(4);
		PointF point5 = pointArray.get(5);
		PointF point0124 = Intersection(point0.x, point0.y, point1.x, point1.y, point2.x, point2.y, point4.x, point4.y);
		PointF point1345 = Intersection(point1.x, point1.y, point3.x, point3.y, point4.x, point4.y, point5.x, point5.y);

		// 그리기 작업
		m_pathTool.reset();
		m_pathTool.moveTo(point0.x, point0.y);
		m_pathTool.lineTo(point1.x, point1.y);
		m_pathTool.lineTo(point1345.x, point1345.y);
		m_pathTool.moveTo(point0124.x, point0124.y);
		m_pathTool.lineTo(point4.x, point4.y);
		m_pathTool.lineTo(point5.x, point5.y);
		canvas.drawPath(m_pathTool, m_lineBrush);

		m_lineBrush.setColor(COMMON_LINE_COLOR);
		m_pathTool.reset();
		m_pathTool.moveTo(point1.x, point1.y);
		m_pathTool.lineTo(point2.x, point2.y);
		m_pathTool.lineTo(point3.x, point3.y);
		m_pathTool.lineTo(point4.x, point4.y);
		canvas.drawPath(m_pathTool, m_lineBrush);

		m_pathTool.reset();
		m_pathTool.moveTo(point1.x, point1.y);
		m_pathTool.lineTo(point1345.x, point1345.y);
		m_pathTool.lineTo(point4.x, point4.y);
		m_pathTool.lineTo(point0124.x, point0124.y);
		m_pathTool.lineTo(point1.x, point1.y);
		canvas.drawPath(m_pathTool, m_fillBrush);

		m_pathTool.reset();
		m_pathTool.moveTo(point5.x, point5.y);
		m_pathTool.lineTo(pointT.x, pointT.y);
		canvas.drawPath(m_pathTool, m_dashBrush);

		//
		DrawName(point4, labelName, LABEL_TEXT_SIZE, labelGravity, Color.BLACK, canvas);
		DrawName(pointT, targetVal, LABEL_TEXT_SIZE, targetLabelGravity, lineColor, canvas);
	}

	public void DrawPennant(ArrayList<PointF> pointArray, PointF pointT, String targetVal, Canvas canvas, boolean isAsc)
	{
		// 공통 체크 함수
		if (CheckValid(pointArray, 6) == false)
		{
			return;
		}

		// 방향에 따른 옵션 조정
		int labelGravity = Gravity.BOTTOM;
		int targetLabelGravity = Gravity.TOP;
		int lineColor = POS_LINE_COLOR;
		int fillColor = POS_FILL_COLOR;
		String labelName = "";
		if (isAsc == true)
		{
			labelGravity = Gravity.BOTTOM;
			targetLabelGravity = Gravity.TOP;
			lineColor = POS_LINE_COLOR;
			fillColor = POS_FILL_COLOR;
			labelName = "상승 페넌트형";
		}
		else
		{
			labelGravity = Gravity.TOP;
			targetLabelGravity = Gravity.BOTTOM;
			lineColor = NEG_LINE_COLOR;
			fillColor = NEG_FILL_COLOR;
			labelName = "하락 페넌트형";
		}
		m_lineBrush.setColor(lineColor);
		m_fillBrush.setColor(fillColor);
		m_dashBrush.setColor(lineColor);

		// 그릴 포인트 준비
		PointF point0 = pointArray.get(0);
		PointF point1 = pointArray.get(1);
		PointF point2 = pointArray.get(2);
		PointF point3 = pointArray.get(3);
		PointF point4 = pointArray.get(4);
		PointF point5 = pointArray.get(5);
		PointF point0124 = Intersection(point0.x, point0.y, point1.x, point1.y, point2.x, point2.y, point4.x, point4.y);

		// 그리기 작업
		m_pathTool.reset();
		m_pathTool.moveTo(point0.x, point0.y);
		m_pathTool.lineTo(point1.x, point1.y);
		m_pathTool.lineTo(point4.x, point4.y);
		m_pathTool.lineTo(point0124.x, point0124.y);
		m_pathTool.moveTo(point4.x, point4.y);
		m_pathTool.lineTo(point5.x, point5.y);
		canvas.drawPath(m_pathTool, m_lineBrush);

		m_pathTool.reset();
		m_pathTool.moveTo(point1.x, point1.y);
		m_pathTool.lineTo(point4.x, point4.y);
		m_pathTool.lineTo(point0124.x, point0124.y);
		m_pathTool.lineTo(point1.x, point1.y);
		canvas.drawPath(m_pathTool, m_fillBrush);

		m_pathTool.reset();
		m_pathTool.moveTo(point5.x, point5.y);
		m_pathTool.lineTo(pointT.x, pointT.y);
		canvas.drawPath(m_pathTool, m_dashBrush);

		m_lineBrush.setColor(COMMON_LINE_COLOR);
		m_pathTool.reset();
		m_pathTool.moveTo(point1.x, point1.y);
		m_pathTool.lineTo(point2.x, point2.y);
		m_pathTool.lineTo(point3.x, point3.y);
		canvas.drawPath(m_pathTool, m_lineBrush);

		//
		DrawName(point4, labelName, LABEL_TEXT_SIZE, labelGravity, Color.BLACK, canvas);
		DrawName(pointT, targetVal, LABEL_TEXT_SIZE, targetLabelGravity, lineColor, canvas);
	}

	public void DrawRectangle(ArrayList<PointF> pointArray, PointF pointT, String targetVal, Canvas canvas, boolean isAsc)
	{
		// 공통 체크 함수
		if (CheckValid(pointArray, 6) == false)
		{
			return;
		}

		// 방향에 따른 옵션 조정
		int labelGravity = Gravity.BOTTOM;
		int targetLabelGravity = Gravity.TOP;
		int lineColor = POS_LINE_COLOR;
		int fillColor = POS_FILL_COLOR;
		String labelName = "";
		if (isAsc == true)
		{
			labelGravity = Gravity.BOTTOM;
			targetLabelGravity = Gravity.TOP;
			lineColor = POS_LINE_COLOR;
			fillColor = POS_FILL_COLOR;
			labelName = "상승 사각형";
		}
		else
		{
			labelGravity = Gravity.TOP;
			targetLabelGravity = Gravity.BOTTOM;
			lineColor = NEG_LINE_COLOR;
			fillColor = NEG_FILL_COLOR;
			labelName = "하락 사각형";
		}
		m_lineBrush.setColor(lineColor);
		m_fillBrush.setColor(fillColor);
		m_dashBrush.setColor(lineColor);

		// 그릴 포인트 준비
		PointF point0 = pointArray.get(0);
		PointF point1 = pointArray.get(1);
		PointF point2 = pointArray.get(2);
		PointF point3 = pointArray.get(3);
		PointF point4 = pointArray.get(4);
		PointF point5 = pointArray.get(5);
		PointF point0124 = Intersection(point0.x, point0.y, point1.x, point1.y, point2.x, point2.y, point4.x, point4.y);

		// 그리기 작업
		m_pathTool.reset();
		m_pathTool.moveTo(point0.x, point0.y);
		m_pathTool.lineTo(point1.x, point1.y);
		m_pathTool.lineTo(point5.x, point5.y);
		m_pathTool.lineTo(point4.x, point4.y);
		m_pathTool.lineTo(point0124.x, point0124.y);
		canvas.drawPath(m_pathTool, m_lineBrush);

		m_lineBrush.setColor(COMMON_LINE_COLOR);
		m_pathTool.reset();
		m_pathTool.moveTo(point1.x, point1.y);
		m_pathTool.lineTo(point2.x, point2.y);
		m_pathTool.lineTo(point3.x, point3.y);
		m_pathTool.lineTo(point4.x, point4.y);
		canvas.drawPath(m_pathTool, m_lineBrush);

		m_pathTool.reset();
		m_pathTool.moveTo(point1.x, point1.y);
		m_pathTool.lineTo(point5.x, point5.y);
		m_pathTool.lineTo(point4.x, point4.y);
		m_pathTool.lineTo(point0124.x, point0124.y);
		m_pathTool.lineTo(point1.x, point1.y);
		canvas.drawPath(m_pathTool, m_fillBrush);

		m_pathTool.reset();
		m_pathTool.moveTo(point5.x, point5.y);
		m_pathTool.lineTo(pointT.x, pointT.y);
		canvas.drawPath(m_pathTool, m_dashBrush);

		//
		DrawName(point4, labelName, LABEL_TEXT_SIZE, labelGravity, Color.BLACK, canvas);
		DrawName(pointT, targetVal, LABEL_TEXT_SIZE, targetLabelGravity, lineColor, canvas);
	}
	

	public void DrawTriangleSymmentrical(ArrayList<PointF> pointArray, PointF pointT, String targetVal, Canvas canvas, boolean isAsc)
	{
		// 공통 체크 함수
		if (CheckValid(pointArray, 6) == false)
		{
			return;
		}

		// 방향에 따른 옵션 조정
		int labelGravity = Gravity.BOTTOM;
		int targetLabelGravity = Gravity.TOP;
		int lineColor = POS_LINE_COLOR;
		int fillColor = POS_FILL_COLOR;
		String labelName = "";
		if (isAsc == true)
		{
			labelGravity = Gravity.BOTTOM;
			targetLabelGravity = Gravity.TOP;
			lineColor = POS_LINE_COLOR;
			fillColor = POS_FILL_COLOR;
			labelName = "상승 대칭삼각형";
		}
		else
		{
			labelGravity = Gravity.BOTTOM;
			targetLabelGravity = Gravity.BOTTOM;
			lineColor = NEG_LINE_COLOR;
			fillColor = NEG_FILL_COLOR;
			labelName = "하락 대칭삼각형";
		}
		m_lineBrush.setColor(lineColor);
		m_fillBrush.setColor(fillColor);
		m_dashBrush.setColor(lineColor);

		// 그릴 포인트 준비
		PointF point0 = pointArray.get(0);
		PointF point1 = pointArray.get(1);
		PointF point2 = pointArray.get(2);
		PointF point3 = pointArray.get(3);
		PointF point4 = pointArray.get(4);
		PointF point5 = pointArray.get(5);
		PointF point1305 = Intersection(point1.x, point1.y, point3.x, point3.y, point0.x, point0.y, point5.x, point5.y);
		PointF point135T = Intersection(point1.x, point1.y, point3.x, point3.y, point5.x, point5.y, pointT.x, pointT.y);

		// 그리기 작업
		m_pathTool.reset();
		m_pathTool.moveTo(point1.x, point1.y);
		m_pathTool.lineTo(point1305.x, point1305.y);
		m_pathTool.lineTo(point0.x, point0.y);
		canvas.drawPath(m_pathTool, m_lineBrush);
		m_pathTool.lineTo(point1.x, point1.y);
		canvas.drawPath(m_pathTool, m_fillBrush);

		m_pathTool.reset();
		m_pathTool.moveTo(point135T.x, point135T.y);
		m_pathTool.lineTo(pointT.x, pointT.y);
		canvas.drawPath(m_pathTool, m_dashBrush);

		m_lineBrush.setColor(COMMON_LINE_COLOR);
		m_pathTool.reset();
		m_pathTool.moveTo(point4.x, point4.y);
		m_pathTool.lineTo(point2.x, point2.y);
		m_pathTool.lineTo(point3.x, point3.y);
		m_pathTool.lineTo(point5.x, point5.y);
		m_pathTool.lineTo(point135T.x, point135T.y);
		canvas.drawPath(m_pathTool, m_lineBrush);

		//
		DrawName(point0, labelName, LABEL_TEXT_SIZE, labelGravity, Color.BLACK, canvas);
		DrawName(pointT, targetVal, LABEL_TEXT_SIZE, targetLabelGravity, lineColor, canvas);
	}
	
	public void DrawTriangleAsc(ArrayList<PointF> pointArray, PointF pointT, String targetVal, Canvas canvas)
	{
		// 공통 체크 함수
		if (CheckValid(pointArray, 7) == false)
		{
			return;
		}

		// 방향에 따른 옵션 조정
		int labelGravity = Gravity.BOTTOM;
		int targetLabelGravity = Gravity.TOP;
		int lineColor = POS_LINE_COLOR;
		int fillColor = POS_FILL_COLOR;
		String labelName = "";
		labelGravity = Gravity.BOTTOM;
		targetLabelGravity = Gravity.TOP;
		lineColor = POS_LINE_COLOR;
		fillColor = POS_FILL_COLOR;
		labelName = "상승 삼각형";
		
		m_lineBrush.setColor(lineColor);
		m_fillBrush.setColor(fillColor);
		m_dashBrush.setColor(lineColor);

		// 그릴 포인트 준비
		PointF point0 = pointArray.get(0);
		PointF point1 = pointArray.get(1);
		PointF point2 = pointArray.get(2);
		PointF point3 = pointArray.get(3);
		PointF point4 = pointArray.get(4);
		PointF point5 = pointArray.get(5);
		PointF point6 = pointArray.get(6);
		PointF point156T = Intersection(point1.x, point1.y, point5.x, point5.y, point6.x, point6.y, pointT.x, pointT.y);

		// 그리기 작업
		m_pathTool.reset();
		m_pathTool.moveTo(point1.x, point1.y);
		m_pathTool.lineTo(point2.x, point2.y);
		m_pathTool.lineTo(point0.x, point0.y);
		canvas.drawPath(m_pathTool, m_lineBrush);
		m_pathTool.lineTo(point1.x, point1.y);
		canvas.drawPath(m_pathTool, m_fillBrush);

		m_pathTool.reset();
		m_pathTool.moveTo(point156T.x, point156T.y);
		m_pathTool.lineTo(pointT.x, pointT.y);
		canvas.drawPath(m_pathTool, m_dashBrush);

		m_lineBrush.setColor(COMMON_LINE_COLOR);
		m_pathTool.reset();
		m_pathTool.moveTo(point3.x, point3.y);
		m_pathTool.lineTo(point4.x, point4.y);
		m_pathTool.lineTo(point5.x, point5.y);
		m_pathTool.lineTo(point6.x, point6.y);
		m_pathTool.lineTo(point156T.x, point156T.y);
		canvas.drawPath(m_pathTool, m_lineBrush);

		//
		DrawName(point0, labelName, LABEL_TEXT_SIZE, labelGravity, Color.BLACK, canvas);
		DrawName(pointT, targetVal, LABEL_TEXT_SIZE, targetLabelGravity, lineColor, canvas);
	}

	public void DrawTriangleDes(ArrayList<PointF> pointArray, PointF pointT, String targetVal, Canvas canvas)
	{
		// 공통 체크 함수
		if (CheckValid(pointArray, 8) == false)
		{
			return;
		}

		// 방향에 따른 옵션 조정
		int labelGravity = Gravity.BOTTOM;
		int targetLabelGravity = Gravity.TOP;
		int lineColor = POS_LINE_COLOR;
		int fillColor = POS_FILL_COLOR;
		String labelName = "";
		labelGravity = Gravity.BOTTOM;
		targetLabelGravity = Gravity.BOTTOM;
		lineColor = NEG_LINE_COLOR;
		fillColor = NEG_FILL_COLOR;
		labelName = "하락 삼각형";
		
		m_lineBrush.setColor(lineColor);
		m_fillBrush.setColor(fillColor);
		m_dashBrush.setColor(lineColor);

		// 그릴 포인트 준비
		PointF point0 = pointArray.get(0);
		PointF point1 = pointArray.get(1);
		PointF point2 = pointArray.get(2);
		PointF point3 = pointArray.get(3);
		PointF point4 = pointArray.get(4);
		PointF point5 = pointArray.get(5);
		PointF point6 = pointArray.get(6);
		PointF point7 = pointArray.get(7);
		PointF point067T = Intersection(point0.x, point0.y, point6.x, point6.y, point7.x, point7.y, pointT.x, pointT.y);

		// 그리기 작업
		m_pathTool.reset();
		m_pathTool.moveTo(point1.x, point1.y);
		m_pathTool.lineTo(point2.x, point2.y);
		m_pathTool.lineTo(point0.x, point0.y);
		canvas.drawPath(m_pathTool, m_lineBrush);
		m_pathTool.lineTo(point1.x, point1.y);
		canvas.drawPath(m_pathTool, m_fillBrush);

		m_pathTool.reset();
		m_pathTool.moveTo(point067T.x, point067T.y);
		m_pathTool.lineTo(pointT.x, pointT.y);
		canvas.drawPath(m_pathTool, m_dashBrush);

		m_lineBrush.setColor(COMMON_LINE_COLOR);
		m_pathTool.reset();
		m_pathTool.moveTo(point3.x, point3.y);
		m_pathTool.lineTo(point4.x, point4.y);
		m_pathTool.lineTo(point5.x, point5.y);
		m_pathTool.lineTo(point6.x, point6.y);
		m_pathTool.lineTo(point7.x, point7.y);
		m_pathTool.lineTo(point067T.x, point067T.y);
		canvas.drawPath(m_pathTool, m_lineBrush);

		//
		DrawName(point4, labelName, LABEL_TEXT_SIZE, labelGravity, Color.BLACK, canvas);
		DrawName(pointT, targetVal, LABEL_TEXT_SIZE, targetLabelGravity, lineColor, canvas);
	}

	public void DrawWedge(ArrayList<PointF> pointArray, PointF pointT, String targetVal, Canvas canvas, boolean isFalling)
	{
		// 공통 체크 함수
		if (CheckValid(pointArray, 8) == false)
		{
			return;
		}

		// 방향에 따른 옵션 조정
		int labelGravity = Gravity.BOTTOM;
		int targetLabelGravity = Gravity.TOP;
		int lineColor = POS_LINE_COLOR;
		int fillColor = POS_FILL_COLOR;
		PointF labelPoint;
		String labelName = "";
		
		// 그릴 포인트 준비
		PointF point0 = pointArray.get(0);
		PointF point1 = pointArray.get(1);
		PointF point2 = pointArray.get(2);
		PointF point3 = pointArray.get(3);
		PointF point4 = pointArray.get(4);
		PointF point5 = pointArray.get(5);
		PointF point6 = pointArray.get(6);
		PointF point7 = pointArray.get(7);
		PointF point137T = Intersection(point1.x, point1.y, point3.x, point3.y, point7.x, point7.y, pointT.x, pointT.y);

		
		if (isFalling == true)
		{
			labelGravity = Gravity.BOTTOM;
			targetLabelGravity = Gravity.TOP;
			lineColor = POS_LINE_COLOR;
			fillColor = POS_FILL_COLOR;
			labelName = "하락 쐐기형";
			labelPoint = point5;
		}
		else
		{
			labelGravity = Gravity.BOTTOM;
			targetLabelGravity = Gravity.BOTTOM;
			lineColor = NEG_LINE_COLOR;
			fillColor = NEG_FILL_COLOR;
			labelName = "상승 쐐기형";
			labelPoint = point6;
		}
		m_lineBrush.setColor(lineColor);
		m_fillBrush.setColor(fillColor);
		m_dashBrush.setColor(lineColor);


		// 그리기 작업
		m_pathTool.reset();
		m_pathTool.moveTo(point1.x, point1.y);
		m_pathTool.lineTo(point3.x, point3.y);
		m_pathTool.moveTo(point0.x, point0.y);
		m_pathTool.lineTo(point2.x, point2.y);
		canvas.drawPath(m_pathTool, m_lineBrush);

		m_pathTool.reset();
		m_pathTool.moveTo(point137T.x, point137T.y);
		m_pathTool.lineTo(pointT.x, pointT.y);
		canvas.drawPath(m_pathTool, m_dashBrush);

		m_lineBrush.setColor(COMMON_LINE_COLOR);
		m_pathTool.reset();
		m_pathTool.moveTo(point4.x, point4.y);
		m_pathTool.lineTo(point5.x, point5.y);
		m_pathTool.lineTo(point6.x, point6.y);
		m_pathTool.lineTo(point7.x, point7.y);
		m_pathTool.lineTo(point137T.x, point137T.y);
		canvas.drawPath(m_pathTool, m_lineBrush);

		//
		DrawName(labelPoint, labelName, LABEL_TEXT_SIZE, labelGravity, Color.BLACK, canvas);
		DrawName(pointT, targetVal, LABEL_TEXT_SIZE, targetLabelGravity, lineColor, canvas);
	}
	
	public void DrawHeadShoulders(ArrayList<PointF> pointArray, PointF pointT, String targetVal, Canvas canvas, boolean isTop)
	{
		// 공통 체크 함수
		if (CheckValid(pointArray, 7) == false)
		{
			return;
		}

		// 방향에 따른 옵션 조정
		int labelGravity = Gravity.BOTTOM;
		int targetLabelGravity = Gravity.TOP;
		int lineColor = POS_LINE_COLOR;
		int fillColor = POS_FILL_COLOR;
		PointF labelPoint;
		String labelName = "";
		
		// 그릴 포인트 준비
		PointF point0 = pointArray.get(0);
		PointF point1 = pointArray.get(1);
		PointF point2 = pointArray.get(2);
		PointF point3 = pointArray.get(3);
		PointF point4 = pointArray.get(4);
		PointF point5 = pointArray.get(5);
		PointF point6 = pointArray.get(6);
		PointF point7 = pointArray.get(7);
		PointF point137T = Intersection(point1.x, point1.y, point3.x, point3.y, point7.x, point7.y, pointT.x, pointT.y);

		
		if (isTop == true)
		{
			labelGravity = Gravity.BOTTOM;
			targetLabelGravity = Gravity.TOP;
			lineColor = POS_LINE_COLOR;
			fillColor = POS_FILL_COLOR;
			labelName = "하락 쐐기형";
			labelPoint = point5;
		}
		else
		{
			labelGravity = Gravity.BOTTOM;
			targetLabelGravity = Gravity.BOTTOM;
			lineColor = NEG_LINE_COLOR;
			fillColor = NEG_FILL_COLOR;
			labelName = "상승 쐐기형";
			labelPoint = point6;
		}
		m_lineBrush.setColor(lineColor);
		m_fillBrush.setColor(fillColor);
		m_dashBrush.setColor(lineColor);


		// 그리기 작업
		m_pathTool.reset();
		m_pathTool.moveTo(point1.x, point1.y);
		m_pathTool.lineTo(point3.x, point3.y);
		m_pathTool.moveTo(point0.x, point0.y);
		m_pathTool.lineTo(point2.x, point2.y);
		canvas.drawPath(m_pathTool, m_lineBrush);

		m_pathTool.reset();
		m_pathTool.moveTo(point137T.x, point137T.y);
		m_pathTool.lineTo(pointT.x, pointT.y);
		canvas.drawPath(m_pathTool, m_dashBrush);

		m_lineBrush.setColor(COMMON_LINE_COLOR);
		m_pathTool.reset();
		m_pathTool.moveTo(point4.x, point4.y);
		m_pathTool.lineTo(point5.x, point5.y);
		m_pathTool.lineTo(point6.x, point6.y);
		m_pathTool.lineTo(point7.x, point7.y);
		m_pathTool.lineTo(point137T.x, point137T.y);
		canvas.drawPath(m_pathTool, m_lineBrush);

		//
		DrawName(labelPoint, labelName, LABEL_TEXT_SIZE, labelGravity, Color.BLACK, canvas);
		DrawName(pointT, targetVal, LABEL_TEXT_SIZE, targetLabelGravity, lineColor, canvas);
	}

	protected PointF Intersection(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
	{
		float d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (d == 0)
		{
			return null;
		}

		float xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
		float yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

		return new PointF(xi, yi);
	}

	protected void DrawName(PointF basePoint, String name, int textSPSize, int align, int color, Canvas canvas)
	{
		Resources r = getResources();
		float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSPSize, r.getDisplayMetrics());

		float yPos = basePoint.y;
		switch (align)
		{
			case Gravity.BOTTOM:
			{
				yPos += textSize;
				break;
			}
			case Gravity.TOP:
			default:
			{
			}
		}

		m_labelBrush.setColor(color);
		m_labelBrush.setTextSize(textSize);
		canvas.drawText(name, basePoint.x, yPos, m_labelBrush);
	}
	
}

// samplePoints.add(new PointF(20, 280));
// samplePoints.add(new PointF(108, 125));
// samplePoints.add(new PointF(123, 168));
// samplePoints.add(new PointF(151, 142));
// samplePoints.add(new PointF(175, 187));
// samplePoints.add(new PointF(210, 123));
// DrawFlag(samplePoints, new PointF(259, 30), canvas , true);
// samplePoints.add(new PointF(2, 8 ));
// samplePoints.add(new PointF(118, 275));
// samplePoints.add(new PointF(139, 200));
// samplePoints.add(new PointF(178, 240));
// samplePoints.add(new PointF(210, 163));
// samplePoints.add(new PointF(261, 277));
// DrawFlag(samplePoints, new PointF(328, 429), "10,000", canvas , false);
// samplePoints.add(new PointF(23, 456));
// samplePoints.add(new PointF(147, 188));
// samplePoints.add(new PointF(160, 250));
// samplePoints.add(new PointF(194, 222));
// samplePoints.add(new PointF(251, 268));
// samplePoints.add(new PointF(290, 183));
// DrawPennant(samplePoints, new PointF(373, 7), "10,000", canvas, true);
// samplePoints.add(new PointF(36, 43));
// samplePoints.add(new PointF(157, 327));
// samplePoints.add(new PointF(178, 266));
// samplePoints.add(new PointF(211, 289));
// samplePoints.add(new PointF(277, 236));
// samplePoints.add(new PointF(316, 330));
// DrawPennant(samplePoints, new PointF(400, 518), "10,000", canvas, false);
//samplePoints.add(new PointF(24, 45));
//samplePoints.add(new PointF(118, 309));
//samplePoints.add(new PointF(163, 246));
//samplePoints.add(new PointF(207, 310));
//samplePoints.add(new PointF(263, 245));
//samplePoints.add(new PointF(287, 310));
//DrawRectangle(samplePoints, new PointF(360, 523), "10,000", canvas, false);

//samplePoints.add(new PointF(152, 510));
//samplePoints.add(new PointF(138, 218));
//samplePoints.add(new PointF(281, 464));
//samplePoints.add(new PointF(359, 282));
//samplePoints.add(new PointF(198, 237));
//samplePoints.add(new PointF(419, 416));
//DrawTriangleSymmentrical(samplePoints, new PointF(599, 57), "10,000", canvas, true);

//samplePoints.add(new PointF(83, 561));
//samplePoints.add(new PointF(82, 278));
//samplePoints.add(new PointF(555, 283));
//samplePoints.add(new PointF(140, 281));
//samplePoints.add(new PointF(199, 489));
//samplePoints.add(new PointF(266, 280));
//samplePoints.add(new PointF(333, 415));
//DrawTriangleAsc(samplePoints, new PointF(499, 602), "10,000", canvas);

//samplePoints.add(new PointF(99, 358));
//samplePoints.add(new PointF(99, 77));
//samplePoints.add(new PointF(593, 361));
//samplePoints.add(new PointF(164, 112));
//samplePoints.add(new PointF(213, 360));
//samplePoints.add(new PointF(284, 178));
//samplePoints.add(new PointF(328, 361));
//DrawTriangleDes(samplePoints, new PointF(499, 602), "10,000", canvas);

//samplePoints.add(new PointF(130, 494));
//samplePoints.add(new PointF(115, 222));
//samplePoints.add(new PointF(580, 572));
//samplePoints.add(new PointF(594, 514));
//samplePoints.add(new PointF(177, 258));
//samplePoints.add(new PointF(238, 514));
//samplePoints.add(new PointF(302, 335));
//samplePoints.add(new PointF(348, 532));
//DrawWedge(samplePoints, new PointF(530, 68), "10,000", canvas , true);

//samplePoints.add(new PointF(133, 462));
//samplePoints.add(new PointF(122, 162));
//samplePoints.add(new PointF(657, 165));
//samplePoints.add(new PointF(639, 69));
//samplePoints.add(new PointF(216, 415));
//samplePoints.add(new PointF(251, 136));
//samplePoints.add(new PointF(358, 331));
//samplePoints.add(new PointF(385, 117));
//DrawWedge(samplePoints, new PointF(616, 616), "10,000", canvas , false);
