package ui;

public class Info {
    public static final String REGOLE = """

        ===================================================================
                                REGOLE MINDQUEST
        ===================================================================
        
        -------------------------------------------------------------------
        1. STRUTTURA DEL TURNO
        -------------------------------------------------------------------
        Ogni turno si articola in 5 fasi consecutive:
        1. Lancio del Dado: Determina lo spostamento e attiva bonus/malus del dado.
        2. Spostamento: La pedina avanza sul tabellone in base al risultato.
        3. Attivazione Casella: Si attiva l'effetto (Domanda o Evento Speciale).
        4. Abilità della Pedina: Si innescano i vantaggi unici del personaggio.
        5. Controllo Vittoria: Il sistema verifica se i requisiti sono soddisfatti.

        -------------------------------------------------------------------
        2. CONDIZIONI DI VITTORIA
        -------------------------------------------------------------------
        Ci sono due modi alternativi per trionfare in MindQuest:
        - VITTORIA PER PERCORSO: Raggiungere per primi l'ultima casella (Casella 70).
        - VITTORIA PER CONOSCENZA: Raggiungere o superare la soglia stabilita
          di Punti Conoscenza, ovunque ci si trovi sul tabellone!

        -------------------------------------------------------------------
        3. GLI ESPLORATORI DEL SAPERE (LE PEDINE)
        -------------------------------------------------------------------
        Ogni giocatore riceve all'inizio una pedina casuale con poteri unici:
        
        - IL SAGGIO:
          Estremamente preparato. Ottiene +2 punti conoscenza extra per ogni
          risposta esatta fornita.
          
        - LO STUDIOSO:
          Costante e metodico. Se riesce a rispondere correttamente a 5 domande
          DIFFICILI di fila, i suoi punti conoscenza complessivi raddoppiano!
          Se sbaglia una domanda difficile, il contatore si azzera.
          
        - LO SPERIMENTATORE:
          Ama il rischio. Quando risponde correttamente a una domanda DIFFICILE,
          ottiene un super bonus di +5 punti conoscenza extra!
          
        - IL FORTUNATO:
          Baciato dalla sorte. Ogni volta che ottiene un 6 esatto dal lancio
          del suo dado, riceve immediatamente +6 punti conoscenza extra!

        -------------------------------------------------------------------
        4. I DADI DEL DESTINO
        -------------------------------------------------------------------
        La gestione del movimento è parte della strategia grazie a dadi speciali:
        - DADO NORMALE: Il classico dado a 6 facce per un movimento standard.
        - DADO INTELLETTUALE: Privilegia i tiri più bassi (estrae il minimo tra
          due lanci), ma regala stabilmente +1 punto conoscenza a ogni lancio!

        -------------------------------------------------------------------
        5. LE CASELLE DEL TABELLONE
        -------------------------------------------------------------------
        Il percorso (composto da 70 caselle) è suddiviso in:
        
        A) CASELLE CONOSCENZA (Prove di cultura generale per macro-aree):
           - Materie disponibili: Scienza, Storia, Arte, Letteratura, Geografia.
           - Domanda Facile: Guadagni 3 punti se indovini, perdi 3 punti se sbagli.
           - Domanda Difficile: Guadagni 5 punti se indovini, perdi solo 1 punto se sbagli.
           
        B) CASELLE EVENTO / BONUS:
           Posizionate regolarmente lungo il percorso (multipli di 5), attivano
           dinamiche impreviste gestite da strategie dedicate, tra cui:
           - Roulette dei Punti Conoscenza: Modifica casualmente i tuoi punti.
           - Sfida 1 VS 1 a Oltranza: Una sfida diretta a domande con un avversario.
           - Trappola della Maledizione: Un evento nefasto che infligge malus immediati.
        ===================================================================
        """;
        
    public static final String PRESENTAZIONE_GIOCO = """
        =================================================================================
                     __  __ ___ _   _ ____   ___  _   _ _____ ____ _____ 
                    |  \\/  |_ _| \\ | |  _ \\ / _ \\| | | | ____/ ___|_   _|
                    | |\\/| || ||  \\| | | | | | | | | | |  _| \\___ \\ | |  
                    | |  | || || |\\  | |_| | |_| | |_| | |___ ___) || |  
                    |_|  |_|___|_| \\_|____/ \\__\\_\\\\___/|_____|____/ |_|  
                                                                         
        =================================================================================
                       Benvenuti in MINDQUEST - Il gioco della conoscenza!
         \s
          In questo gioco, affronterete sfide di conoscenza per avanzare sul tabellone
                                 e guadagnare punti conoscenza.
         \s
             Seguite il percorso con saggezza e cercate di accumulare più punti!
        =================================================================================
        
        Premere INVIO per iniziare la vostra avventura...
        """;
}
