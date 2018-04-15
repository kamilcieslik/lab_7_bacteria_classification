package javafx;


public class UnclassifiedBacteria {
    private String genotype;
    private Integer alpha;
    private Integer beta;
    private Integer gamma;

    public UnclassifiedBacteria(String genotype) {
        this.genotype = genotype;
        convertPartsOfTheGenotype();
    }

    public String getGenotype() {
        return genotype;
    }

    public Integer getAlpha() {
        return alpha;
    }

    public Integer getBeta() {
        return beta;
    }

    public Integer getGamma() {
        return gamma;
    }

    private void convertPartsOfTheGenotype(){
        String partOfTheGenotype;
        partOfTheGenotype=String.valueOf(genotype.charAt(0))+String.valueOf(genotype.charAt(5));
        alpha=Integer.parseInt(partOfTheGenotype);

        partOfTheGenotype=String.valueOf(genotype.charAt(1))+String.valueOf(genotype.charAt(4));
        beta=Integer.parseInt(partOfTheGenotype);

        partOfTheGenotype=String.valueOf(genotype.charAt(2))+String.valueOf(genotype.charAt(3));
        gamma=Integer.parseInt(partOfTheGenotype);
    }

    @Override
    public String toString() {
        return "UnclassifiedBacteria{" +
                "genotype='" + genotype + '\'' +
                ", alpha=" + alpha +
                ", beta=" + beta +
                ", gamma=" + gamma +
                '}';
    }
}
