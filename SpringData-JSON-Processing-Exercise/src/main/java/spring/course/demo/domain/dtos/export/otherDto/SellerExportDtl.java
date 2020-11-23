package spring.course.demo.domain.dtos.export.otherDto;

public class SellerExportDtl {
    private String firsName;
    private String lastName;

    public SellerExportDtl() {
    }

    public String getFullName() {
        return firsName + " " + lastName;
    }
}
