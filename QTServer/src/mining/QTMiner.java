package mining;

import data.Data;
import data.Tuple;
import java.io.*;

/**
 * Raggruppa gli esempi del dataset in cluster in base a un raggio massimo, e supporta il salvataggio e il caricamento dei risultati su file.
 */
public class QTMiner implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Insieme dei cluster prodotti dall'algoritmo. */
    private ClusterSet C;

    /** Raggio massimo entro cui un esempio può appartenere a un cluster. */
    private double radius;

    /**
     * Costruisce un oggetto QTMiner con il raggio specificato.
     *
     * @param radius raggio massimo per l'assegnazione degli esempi ai cluster
     */
    public QTMiner(double radius) {
        this.C = new ClusterSet();
        this.radius = radius;
    }

    /**
     * Deserializzazione.
     *
     * @param fileName nome del file da cui caricare il QTMiner serializzato
     * @throws FileNotFoundException  se il file non viene trovato
     * @throws IOException            se si verifica un errore di lettura
     * @throws ClassNotFoundException se la classe dell'oggetto letto non è trovata
     */
    public QTMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        QTMiner qt = (QTMiner) in.readObject();
        this.C = qt.C;
        this.radius = qt.radius;
        in.close();
        fileIn.close();
    }

    /**
     * Serializzazione.
     *
     * @param fileName nome del file in cui salvare il QTMiner
     * @throws FileNotFoundException se il file non può essere creato
     * @throws IOException           se si verifica un errore di scrittura
     */
    public void salva(String fileName) throws FileNotFoundException, IOException {
        FileOutputStream fileOut = new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this);
        out.close();
        fileOut.close();
    }

    /**
     * Restituisce l'insieme dei cluster prodotti dall'algoritmo.
     *
     * @return insieme dei cluster
     */
    public ClusterSet getC() {
        return C;
    }

    /**
     * Esegue l'algoritmo QT sul dataset specificato e restituisce il numero di cluster formati.
     *
     * @param data dataset su cui eseguire il clustering
     * @return     numero di cluster formati
     * @throws ClusteringRadiusException se il raggio produce un solo cluster
     */
    public int compute(Data data) throws ClusteringRadiusException {
        int numclusters = 0;
        boolean isClustered[] = new boolean[data.getNumberOfExamples()];
        for (int i = 0; i < isClustered.length; i++) {
            isClustered[i] = false;
        }

        int countClustered = 0;
        while (countClustered != data.getNumberOfExamples()) {
            Cluster c = buildCandidateCluster(data, isClustered);
            if (c == null) break;

            C.add(c);
            numclusters++;

            for (int id : c) {
                isClustered[id] = true;
                countClustered++;
            }
        }

        if (numclusters == 1) {
            throw new ClusteringRadiusException();
        }

        return numclusters;
    }

    /**
     * Costruisce il cluster candidato con il maggior numero di esempi  entro il raggio specificato, considerando solo gli esempi non ancora clusterizzati.
     *
     * @param data        dataset contenente gli esempi
     * @param isClustered array che indica quali esempi sono già stati assegnati a un cluster
     * @return            cluster candidato con il maggior numero di esempi, null se non trovato
     */
    private Cluster buildCandidateCluster(Data data, boolean isClustered[]) {
        Cluster bestCluster = null;
        int maxSize = -1;

        for (int i = 0; i < data.getNumberOfExamples(); i++) {
            if (!isClustered[i]) {
                Tuple centroid = data.getItemSet(i);
                Cluster currentCluster = new Cluster(centroid);
                currentCluster.addData(i);

                for (int j = 0; j < data.getNumberOfExamples(); j++) {
                    if (!isClustered[j] && i != j) {
                        double distance = centroid.getDistance(data.getItemSet(j));
                        if (distance <= radius) {
                            currentCluster.addData(j);
                        }
                    }
                }

                if (currentCluster.getSize() > maxSize) {
                    maxSize = currentCluster.getSize();
                    bestCluster = currentCluster;
                }
            }
        }
        return bestCluster;
    }
}