package utils;

import java.util.List;

public class RahulPojo {
    private String instructor;
    private String url;
    private String services;
    private String expertise;
    private CoursePojo courses;
    private String linkedIn;

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public CoursePojo getCourses() {
        return courses;
    }

    public void setCourses(CoursePojo courses) {
        this.courses = courses;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public static class CoursePojo {
        private List<WebAutomationPojo> webAutomation;
        private List<WebAutomationPojo> api;
        private List<WebAutomationPojo> mobile;

        public List<WebAutomationPojo> getWebAutomation() {
            return webAutomation;
        }

        public void setWebAutomation(List<WebAutomationPojo> webAutomation) {
            this.webAutomation = webAutomation;
        }

        public List<WebAutomationPojo> getApi() {
            return api;
        }

        public void setApi(List<WebAutomationPojo> api) {
            this.api = api;
        }

        public List<WebAutomationPojo> getMobile() {
            return mobile;
        }

        public void setMobile(List<WebAutomationPojo> mobile) {
            this.mobile =  mobile;
        }

        public static class WebAutomationPojo {
            private String courseTitle;
            private int price;

            public String getCourseTitle() {
                return courseTitle;
            }

            public void setCourseTitle(String courseTitle) {
                this.courseTitle = courseTitle;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }
        }


    }
}
