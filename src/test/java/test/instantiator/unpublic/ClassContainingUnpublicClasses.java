package test.instantiator.unpublic;


public class ClassContainingUnpublicClasses {

    class Package_PublicConstructor {
        public Package_PublicConstructor() {
        }
    }

    class Package_PackageConstructor {
        Package_PackageConstructor() {
        }
    }

    class Package_ProtectedConstructor {
        protected Package_ProtectedConstructor() {
        }
    }

    class Package_PrivateConstructor {
        private Package_PrivateConstructor() {
        }
    }

    protected class Protected_PublicConstructor {
        public Protected_PublicConstructor() {
        }
    }

    protected class Protected_PackageConstructor {
        Protected_PackageConstructor() {
        }
    }

    protected class Protected_ProtectedConstructor {
        protected Protected_ProtectedConstructor() {
        }
    }

    protected class Protected_PrivateConstructor {
        private Protected_PrivateConstructor() {
        }
    }

    private class Private_PublicConstructor {
        public Private_PublicConstructor() {
        }
    }

    private class Private_PackageConstructor {
        Private_PackageConstructor() {
        }
    }

    private class Private_ProtectedConstructor {
        protected Private_ProtectedConstructor() {
        }
    }

    private class Private_PrivateConstructor {
        private Private_PrivateConstructor() {
        }
    }

}
