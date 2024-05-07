package ru.nsu.kondrenko.model.context;

import ru.nsu.kondrenko.model.CommonUtils;
import ru.nsu.kondrenko.model.dto.Double2DPoint;
import ru.nsu.kondrenko.model.dto.IntPoint;

public class ContextUtils {
    public static Double2DPoint screenToRealBspline(IntPoint screenPoint, Context context) {
        return CommonUtils.screenToReal(
                screenPoint,
                context.getBSplineWidth(),
                context.getBSplineHeight(),
                context.getBSplineMinX(),
                context.getBSplineMaxX(),
                context.getBSplineMinY(),
                context.getBSplineMaxY()
        );
    }

    public static Double2DPoint screenToRealWireframe(IntPoint screenPoint, Context context) {
        return CommonUtils.screenToReal(
                screenPoint,
                context.getWireframeWidth(),
                context.getWireframeHeight(),
                context.getWireframeMinX(),
                context.getWireframeMaxX(),
                context.getWireframeMinY(),
                context.getWireframeMaxY()
        );
    }

    public static IntPoint realToScreenBSpline(Double2DPoint realPoint, Context context) {
        return CommonUtils.realToScreen(
                realPoint,
                context.getBSplineWidth(),
                context.getBSplineHeight(),
                context.getBSplineMinX(),
                context.getBSplineMaxX(),
                context.getBSplineMinY(),
                context.getBSplineMaxY()
        );
    }

    public static IntPoint realToScreenWireframe(Double2DPoint realPoint, Context context) {
        return CommonUtils.realToScreen(
                realPoint,
                context.getWireframeWidth(),
                context.getWireframeHeight(),
                context.getWireframeMinX(),
                context.getWireframeMaxX(),
                context.getWireframeMinY(),
                context.getWireframeMaxY()
        );
    }
}
