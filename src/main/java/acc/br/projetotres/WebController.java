package acc.br.projetotres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

    @Autowired
    public ScoreRepository scoreRepo;

    @ResponseBody
    @GetMapping("/score")
    public Score getScore() {
        Score score;
        try {
            score = scoreRepo.findById(1).orElseThrow();
        } catch (Exception e) {
            score = new Score(0, 0, 0);
            scoreRepo.save(score);
        }
        return score;
    }
    @RequestMapping("/resultado")
    public String resultado(Model model, WebController scoreService) {
        Score score = scoreService.getScore();
        model.addAttribute("score", score);
        return "resultado";
    }





    @GetMapping("/teste")
    public String teste(@RequestParam(name = "escolha") String aEscolha, Model model) {
        String saida = "empate";
        Score score = getScore();

        if (aEscolha.equalsIgnoreCase("papel")) {
            saida = "ganhou";
            score.setVitorias(score.getVitorias() + 1);
        } else if (aEscolha.equalsIgnoreCase("tesoura")) {
            saida = "perdeu";
            score.setDerrotas(score.getDerrotas() + 1);
        } else {
            score.setEmpates(score.getEmpates() + 1);
        }

        scoreRepo.save(score);
        model.addAttribute("saida", saida);
        model.addAttribute("aEscolha", aEscolha);
        return "resultado";
    }

    @ResponseBody
    @GetMapping("/zerarScore")
    public String zerarScore() {
        Score score = getScore();
        score.setVitorias(0);
        score.setDerrotas(0);
        score.setEmpates(0);
        scoreRepo.save(score);
        return "Score zerado com sucesso!";
    }
}


