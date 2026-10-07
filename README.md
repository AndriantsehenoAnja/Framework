# Framework
## utilisation de la framework
- envoy des Object vers controllers : 
    exemple:- model user avec attribut : name et password
            - dans controller :{void save(@AnjaParameter ("user") user)}
            - dans le jsp doit etre name = "user.name" et name = "user.password"