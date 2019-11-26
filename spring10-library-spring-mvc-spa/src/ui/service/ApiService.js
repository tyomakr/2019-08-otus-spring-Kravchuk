import axios from 'axios';


const API_URL = '/api/v1/';
const API_BOOKS_URL = API_URL + 'books/';
const API_AUTHORS_URL = API_URL + 'authors/';
const API_GENRES_URL = API_URL + 'genres/';

export default new class ApiService {


    fetchAllBooks() {
        return fetch(API_BOOKS_URL);
    }

    fetchBook(id) {
        return axios.get(API_BOOKS_URL + window.localStorage.getItem(id))
    }

    saveBook(book) {
        return axios.put(API_BOOKS_URL + 'update/', book);
    }

    createBook(data) {
        return axios.post(API_BOOKS_URL, data)
    }

    deleteBook(id) {
        return axios.delete(API_BOOKS_URL + id)
    }

    fetchCommentsByBook(id) {
        return axios.get(API_BOOKS_URL + window.localStorage.getItem(id) + '/comments/')
    }

    fetchAllAuthors() {
        return fetch(API_AUTHORS_URL);
    }

    fetchAllGenres() {
        return fetch(API_GENRES_URL);
    }




}