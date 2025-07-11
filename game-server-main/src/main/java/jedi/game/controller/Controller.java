package jedi.game.controller;//package jedi.game.controller;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api")
//public class Controller {
//
//
//    /**
//     * 示例：GET /api/forward?url=https://httpbin.org/get
//     */
//    @GetMapping("/run")
//    public ResponseEntity<List<String>> runBattle(
//            @RequestParam(required = false) String actor,
//            @RequestParam(required = false) String zhaoSkills,
//            @RequestParam(required = false) String lvSkills
//    ) {
//        // 打印接收到的参数（可调试用）
//        System.out.println("actor: " + actor);
//        System.out.println("赵云技能: " + zhaoSkills);
//        System.out.println("吕布技能: " + lvSkills);
//
//        // 可以将技能字符串用逗号分割成列表
//        List<Integer> zhaoSkillList = parseSkills(zhaoSkills);
//        List<Integer> lvSkillList = parseSkills(lvSkills);
//
//        // 你的战斗逻辑在这里处理...
//        List<String> result = BattleSystemDemo.runBattle(zhaoSkillList, lvSkillList);
//        return ResponseEntity.ok(result);
//    }
//
//
//    private List<Integer> parseSkills(String skills) {
//        if (skills == null || skills.isEmpty()) return Collections.emptyList();
//        return Arrays.stream(skills.split(","))
//                .map(Integer::parseInt)
//                .collect(Collectors.toList());
//    }
//
//}
