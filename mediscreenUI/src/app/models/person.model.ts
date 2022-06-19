export class Person{
    constructor(
        public id: number,
        public familyName: string,
        public givenName: string,
        public birthdate: Date,
        public address: string,
        public phone: string, 
        public sex: string){}
}