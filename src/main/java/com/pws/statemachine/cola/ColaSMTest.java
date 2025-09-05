package com.pws.statemachine.cola;

import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
// import com.chinacnd.horizon.wms.innotify.entity.WmsInNotify; // 移除不存在的包
import com.google.common.collect.Maps;

import java.util.Map;

public class ColaSMTest {

    public static void main(String[] args) {
        SafeSiteStatusStateMachineFactory.init();

        fire("SITE_STATE_FLOW_COMMON");
        fire("SITE_STATE_FLOW_WEIGHT_FIRST");
    }

    private void test1() {
        /**
         * FLOW 1
         */
        StateMachineBuilder<String, String, Map<String, String>> builder1 = StateMachineBuilderFactory.create();
        builder1.externalTransition()
                .from("STAY_SAMPLING")
                .to("STAY_QUALITY_TESTING")
                .on("DO_SAMPLE")
                .when(context -> true)
                .perform((from, to, event, context) -> System.out.println("do sample"));
        builder1.externalTransition()
                .from("STAY_QUALITY_TESTING")
                .to("STAY_WEIGH")
                .on("DO_QUALITY_TEST")
                .when(context -> true)
                .perform((from, to, event, context) -> System.out.println("do quality test"));
        builder1.externalTransition()
                .from("STAY_WEIGH")
                .to("STAY_TWO_WEIGH")
                .on("DO_WEIGHT")
                .when(context -> true)
                .perform((from, to, event, context) -> System.out.println("do weight"));
        StateMachine<String, String, Map<String, String>> stateMachine1 = builder1.build("SITE_STATUS_FLOW_COMMON");

        /**
         * FLOW 2
         */
        StateMachineBuilder<String, String, Map<String, String>> builder2 = StateMachineBuilderFactory.create();
        builder2.externalTransition()
                .from("STAY_SAMPLING")
                .to("STAY_WEIGH")
                .on("DO_SAMPLE")
                .when(context -> true)
                .perform((from, to, event, context) -> System.out.println("do sample"));
        builder2.externalTransition()
                .from("STAY_WEIGH")
                .to("STAY_QUALITY_TESTING")
                .on("DO_WEIGHT")
                .when(context -> true)
                .perform((from, to, event, context) -> System.out.println("do weight"));
        builder2.externalTransition()
                .from("STAY_QUALITY_TESTING")
                .to("STAY_TWO_WEIGH")
                .on("DO_QUALITY_TEST")
                .when(context -> true)
                .perform((from, to, event, context) -> System.out.println("do quality test"));
        StateMachine<String, String, Map<String, String>> stateMachine2 = builder2.build("SITE_STATUS_FLOW_WEIGHT_FIRST");


        System.out.println("----- FLOW 1 -----");
        String target1 = stateMachine1.fireEvent("STAY_SAMPLING", "DO_SAMPLE", Maps.newHashMap());
        System.out.println("target = " + target1);
        String target2 = stateMachine1.fireEvent("STAY_WEIGH", "DO_WEIGHT", Maps.newHashMap());
        System.out.println("target = " + target2);
        String target3 = stateMachine1.fireEvent("STAY_QUALITY_TESTING", "DO_QUALITY_TEST", Maps.newHashMap());
        System.out.println("target = " + target3);

        System.out.println("----- FLOW 2 -----");
        String target4 = stateMachine2.fireEvent("STAY_SAMPLING", "DO_SAMPLE", Maps.newHashMap());
        System.out.println("target = " + target4);
        String target5 = stateMachine2.fireEvent("STAY_WEIGH", "DO_WEIGHT", Maps.newHashMap());
        System.out.println("target = " + target5);
        String target6 = stateMachine2.fireEvent("STAY_QUALITY_TESTING", "DO_QUALITY_TEST", Maps.newHashMap());
        System.out.println("target = " + target6);
    }


    private static void fire(String flowId) {
        try {
            SafeStateMachineExecutor<SiteStatusState, SiteStatusEvent, Map<String, String>> executor = SafeSiteStatusStateMachineFactory.safeGet(flowId);

            System.out.println("----- " + flowId + " -----");

            SiteStatusState target1 = executor.verifyAndFire(SiteStatusState.STAY_SAMPLING, SiteStatusEvent.DO_SAMPLE, Maps.newHashMap());
            System.out.println("result = " + target1.getName());

            SiteStatusState target2 = executor.verifyAndFire(SiteStatusState.STAY_QUALITY_TESTING, SiteStatusEvent.DO_QUALITY_TEST, Maps.newHashMap());
            System.out.println("result = " + target2.getName());

            SiteStatusState target3 = executor.verifyAndFire(SiteStatusState.STAY_WEIGH, SiteStatusEvent.DO_WEIGHT, Maps.newHashMap());
            System.out.println("result = " + target3.getName());

            SiteStatusState target4 = executor.verifyAndFire(SiteStatusState.STAY_TWO_WEIGH, SiteStatusEvent.DO_TWO_WEIGHT, Maps.newHashMap());
            System.out.println("result = " + target4.getName());

            SiteStatusState target5 = executor.verifyAndFire(SiteStatusState.STAY_SETTLEMENT, SiteStatusEvent.DO_SETTLEMENT, Maps.newHashMap());
            System.out.println("result = " + target5.getName());

            SiteStatusState target6 = executor.verifyAndFire(SiteStatusState.STAY_QUALITY_TESTING, SiteStatusEvent.DO_WEIGHT, Maps.newHashMap());
            System.out.println("test illegal result = " + target6.getName());
        } catch (Exception e) {
            System.out.println("ERROR! 状态机执行失败：" + e.getMessage());
        }
    }

    private static void scenario() {

        // WmsInNotify wmsInNotify = new WmsInNotify(); // 注释掉不存在的类

        String siteStatusFlowId = "xxx";
//        String siteStatusFlowId = wmsInNotify.getSiteStatusFlowId();

        // String currentSiteStatus = wmsInNotify.getsSiteStatus(); // 注释掉不存在的方法



        fire("SITE_STATE_FLOW_COMMON");
        fire("SITE_STATE_FLOW_WEIGHT_FIRST");
    }


}
