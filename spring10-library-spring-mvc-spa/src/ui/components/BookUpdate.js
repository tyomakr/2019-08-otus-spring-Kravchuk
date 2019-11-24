import React from 'react'
import {Helmet} from "react-helmet/es/Helmet";
import {Header} from "../fragments/Header";
import ApiService from "../service/ApiService";

export default class BookUpdate extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            id: '',
            title: '',
            authors: [],
            genres: []
        };
        this.getBook = this.getBook.bind(this);
        this.showDeleteAuthorButton = this.showDeleteAuthorButton.bind(this);
        this.showDeleteGenreButton = this.showDeleteGenreButton.bind(this);
        this.saveBook = this.saveBook.bind(this);
    }


    componentDidMount() {
        this.getBook();
    }


    //Получение книг по rest
    getBook() {
        ApiService.fetchBook("id")
            .then((res) => {
                let book = res.data.result.body;
                this.setState({
                    id: book.id,
                    title: book.title,
                    authors : book.authors,
                    genres : book.genres,
                })
            });
    }


    //сохранить книгу
    saveBook(e) {
        e.preventDefault();
        let book = {
            id: this.state.id,
            title: this.state.title,
            authors: this.state.authors,
            genres: this.state.genres
        };
        ApiService.saveBook(book)
            .then(response =>{
                this.props.history.push('/Books');
            });
    }



    //показывает кнопку удаления автора, если их больше одного
    showDeleteAuthorButton() {
        return this.state.authors.length > 1;
    }


    //показывает кнопку удаления жанра, если их больше одного
    showDeleteGenreButton() {
        return this.state.genres.length > 1;
    }


    //onChange поля название книги
    handleChange(e) {
        e.preventDefault();
        this.setState({[e.target.name] : e.target.value });
    }


    //изменение поля автора
    handleAuthorChange(id, event) {
        const index = this.state.authors.findIndex((author) => {
            return (author.id === id);
        });

        const author = Object.assign({}, this.state.authors[index]);
        author.authorName = event.target.value;

        const authors = Object.assign([], this.state.authors);
        authors[index] = author;

        this.setState({authors:authors});
    }


    //изменение поля жанра
    handleGenreChange(id, event) {
        const index = this.state.genres.findIndex((genre) => {
            return (genre.id === id);
        });

        const genre = Object.assign({}, this.state.genres[index]);
        genre.genreTitle = event.target.value;

        const genres = Object.assign([], this.state.genres);
        genres[index] = genre;

        this.setState({genres:genres});
    }

    //кнопка удаления автора
    handleAuthorDelete(id) {

        const index = this.state.authors.findIndex((author) => {
            return (author.id === id);
        });

        const authors = Object.assign([], this.state.authors);
        if (index !== -1) {
            authors.splice(index, 1);
        }
        this.setState({authors:authors});
    }

    //кнопка удаления жанра
    handleGenreDelete(id) {

        const index = this.state.genres.findIndex((genre) => {
            return (genre.id === id);
        });

        const genres = Object.assign([], this.state.genres);
        if (index !== -1) {
            genres.splice(index, 1);
        }
        this.setState({genres:genres});
    }



    render() {

        return (
            <React.Fragment>
                <div className={BookUpdate}>
                    <Helmet>
                        <meta charSet="utf-8" />
                        <title>React Spring Library - Редактирование книги</title>
                    </Helmet>
                </div>
                <Header history={this.props.history}/>

                <div className="container mt-3 ml-3">
                    <h2>Редактирование книги</h2>
                </div>
                <div className="container mt-3 ml-3">
                    <form>
                        <div className="form-group input-group mt-3">
                            <label className="col-sm-2 col-form-label" for="id">ID</label>
                            <div className="col-sm-10">
                                <input className="form-control-plaintext" type="text" id="id" name="id" readOnly="true" defaultValue={this.state.id} />
                            </div>
                        </div>

                        <div className="form-group input-group mt-3">
                            <label className="col-sm-2 col-form-label" for="title">Название книги</label>
                            <input className="form-control" type="text" id="title" name="title" defaultValue={this.state.title} onChange={this.handleChange.bind(this)}/>
                        </div>
                        {
                            this.state.authors.map(author =>
                                <div key={author.id} className="input-group-append mt-3 mb-3">
                                    <label className="col-sm-2 col-form-label">Автор</label>
                                    <input className="form-control" id={author.id} name={author.id}
                                           defaultValue={author.authorName} onChange={this.handleAuthorChange.bind(this, author.id)}/>
                                    {
                                        this.showDeleteAuthorButton() &&

                                        <div className="input-group-append">
                                            <button className="btn btn-outline-danger" onClick={this.handleAuthorDelete.bind(this, author.id)}>Удалить автора</button>
                                        </div>
                                    }
                                </div>
                            )
                        }
                        {
                            this.state.genres.map( genre =>
                            <div key={genre.id} className="input-group-append mt-3 mb-3">
                                <label className="col-sm-2 col-form-label">Жанр</label>

                                <input className="form-control" id={genre.id} name={genre.id}
                                       defaultValue={genre.genreTitle} onChange={this.handleGenreChange.bind(this, genre.id)}/>
                                {
                                    this.showDeleteGenreButton() &&

                                    <div className="input-group-append">
                                        <button className="btn btn-outline-danger" onClick={this.handleGenreDelete.bind(this, genre.id)}>Удалить жанр</button>
                                    </div>
                                }
                            </div>
                            )
                        }

                        <button className="btn btn-success" onClick={this.saveBook}>Сохранить изменения</button>
                    </form>
                </div>
            </React.Fragment>
        )
    }
}